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
@ApiModel(value = "[CouponRequestDto] - 쿠폰 요청 정보")
public class CouponRequestDto {

    private Long userSeq;
    private String coupon;
    @Setter
    private CouponStatus status;

    @Builder
    public CouponRequestDto(Long userSeq, String coupon, CouponStatus status) {
        this.userSeq = userSeq;
        this.coupon = coupon;
        this.status = status;
    }
}
