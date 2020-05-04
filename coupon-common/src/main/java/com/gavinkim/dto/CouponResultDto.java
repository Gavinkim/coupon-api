package com.gavinkim.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "[CouponResultDto] - 쿠폰 리스트 정보")
public class CouponResultDto {
    List<CouponDto> coupons = new ArrayList<>();
    PageInfoDto pageInfo;

    public CouponResultDto(Page<CouponDto> couponDtoPage) {
        this.coupons = couponDtoPage.getContent();
        this.pageInfo = PageInfoDto.builder()
                .totalPages(couponDtoPage.getTotalPages())
                .totalElements(couponDtoPage.getTotalElements())
                .build();
    }
}
