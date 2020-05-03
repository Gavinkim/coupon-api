package com.gavinkim.dto;

import com.gavinkim.type.CouponStatus;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "[CouponDto] - 쿠폰 정보")
public class CouponDto {
    private String coupon;
    private String expiredAt;
    private CouponStatus status;

    @Builder
    public CouponDto(String coupon, String expiredAt, CouponStatus status) {
        this.coupon = coupon;
        this.expiredAt = expiredAt;
        this.status = status;
    }
}
