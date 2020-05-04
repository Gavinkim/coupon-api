package com.gavinkim.model.coupon;

import com.gavinkim.dto.CouponDto;
import com.gavinkim.dto.CouponRequestDto;
import com.gavinkim.dto.CouponResultDto;
import com.gavinkim.dto.SearchDto;
import com.gavinkim.model.AlreadyException;
import com.gavinkim.model.BadRequestException;
import com.gavinkim.model.user.User;
import com.gavinkim.model.user.UserService;
import com.gavinkim.type.CouponStatus;
import com.gavinkim.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gavinkim.type.CouponStatus.CANCELED;
import static com.gavinkim.type.CouponStatus.USED;

@Slf4j
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserService userService;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository,
                             UserService userService) {
        this.couponRepository = couponRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public long generateCoupon(long count) {
        String expiredAt = LocalDate.now().plusDays(2).format(Utils.YYYYMMDD);
        List<String> codes = Stream.generate(
                ()-> Utils.generate()).limit(count).collect(Collectors.toList());
        List<Coupon> coupons = codes.stream().map(
                c -> Coupon.builder().coupon(c)
                        //.expiredAt(expiredAt)
                        .build()).collect(Collectors.toList());
        couponRepository.saveAll(coupons);
        return coupons.size();
    }

    @Override
    @Transactional
    //@Lock(LockModeType.PESSIMISTIC_WRITE) //동시 접근 고려
    public String assignCoupon(long userSeq) {
        User user = userService.getUserById(userSeq);
        Coupon foundCoupon = couponRepository.findOneWithNotAssigned()
                .orElseThrow(()->new NotAvailableCouponException("not available coupon"));
        foundCoupon.assign(user.getUserSeq());
        couponRepository.save(foundCoupon);
        return foundCoupon.getCoupon();
    }

    @Override
    public CouponResultDto searchUserCoupon(SearchDto searchDto) {
        validateUser(searchDto);
        Page<CouponDto> couponDtoPage =  couponRepository.getUserCoupon(searchDto);
        return new  CouponResultDto(couponDtoPage);
    }

    @Override
    @Transactional
    public CouponDto manageCoupon(CouponRequestDto couponRequestDto) {
        return process(couponRequestDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CouponResultDto expiredUserCoupon(SearchDto searchDto) {
        validateUser(searchDto);
        Page<CouponDto> couponDtoPage =  couponRepository.getExpiredUserCoupon(searchDto);
        return new  CouponResultDto(couponDtoPage);
    }

    private CouponDto process(CouponRequestDto couponRequestDto){
        validateCouponRequest(couponRequestDto);
        switch(couponRequestDto.getStatus()){
            case USED:
                Coupon useCoupon = couponRepository.getUsingCoupon(couponRequestDto)
                        .orElseThrow(()->new CouponNotFoundException());
                if(useCoupon.getStatus()==couponRequestDto.getStatus()){
                    throw new AlreadyException();
                }
                useCoupon.setStatus(USED);
                return CouponDto.builder()
                        .coupon(useCoupon.getCoupon())
                        .status(USED)
                        .expiredAt(useCoupon.getExpiredAt())
                        .build();
            case CANCELED:
                Coupon cancelCoupon = couponRepository.getCancelCoupon(couponRequestDto)
                        .orElseThrow(()->new CouponNotFoundException());
                if(cancelCoupon.getStatus()==couponRequestDto.getStatus()){
                    throw new AlreadyException();
                }
                cancelCoupon.setStatus(CANCELED);
                return CouponDto.builder()
                        .coupon(cancelCoupon.getCoupon())
                        .status(CANCELED)
                        .expiredAt(cancelCoupon.getExpiredAt())
                        .build();
            default:
                throw new BadRequestException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Coupon>> getBeforeExpireCoupon(LocalDate today, int after) {
        List<Coupon> coupons = couponRepository.getBeforeExpireCoupon(today,after);
        return Optional.ofNullable(coupons);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Coupon>> getExpiredCoupon(LocalDate today) {
        List<Coupon> coupons = couponRepository.getExpiredCoupon(today);
        return Optional.ofNullable(coupons);
    }

    @Override
    @Transactional
    public void saveAll(List<Coupon> coupons) {
        couponRepository.saveAll(coupons);
    }

    private void validateCouponRequest(CouponRequestDto couponRequestDto){
        userService.getUserById(couponRequestDto.getUserSeq());
        Arrays.stream(CouponStatus.values()).filter(c -> c == couponRequestDto.getStatus())
                .findAny()
                .orElseThrow(()->new BadRequestException("쿠폰 상태 값이 잘못 입력 되었습니다. 쿠폰 타입: [USED or CANCEL]"));
    }
    private void validateUser(SearchDto searchDto){
        userService.getUserById(searchDto.getUserSeq());
    }

}
