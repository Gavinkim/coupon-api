package com.gavinkim.model.coupon;

import com.gavinkim.dto.CouponDto;
import com.gavinkim.dto.CouponRequestDto;
import com.gavinkim.dto.CouponResultDto;
import com.gavinkim.dto.SearchDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CouponService {
    long generateCoupon(long count);
    String assignCoupon(long userSeq);
    CouponResultDto searchUserCoupon(SearchDto searchDto);
    CouponDto manageCoupon(CouponRequestDto couponRequestDto);
    CouponResultDto expiredUserCoupon(SearchDto searchDto);
    Optional<List<Coupon>> getBeforeExpireCoupon(LocalDate today, int after);
    Optional<List<Coupon>> getExpiredCoupon(LocalDate today);
    void saveAll(List<Coupon> coupons);
}
