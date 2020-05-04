package com.gavinkim.model.coupon;

import com.gavinkim.dto.CouponDto;
import com.gavinkim.dto.CouponRequestDto;
import com.gavinkim.dto.SearchDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CouponRepositoryCustom {
    Optional<Coupon> findOneWithNotAssigned();
    Page<CouponDto> getUserCoupon(SearchDto searchDto);
    Page<CouponDto> getExpiredUserCoupon(SearchDto searchDto);
    Optional<Coupon> getUsingCoupon(CouponRequestDto couponRequestDto);
    Optional<Coupon> getCancelCoupon(CouponRequestDto couponRequestDto);
    List<Coupon> getBeforeExpireCoupon(LocalDate today,int after);
    List<Coupon> getExpiredCoupon(LocalDate today);
}
