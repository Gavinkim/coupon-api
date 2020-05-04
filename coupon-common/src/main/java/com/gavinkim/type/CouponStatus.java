package com.gavinkim.type;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public enum CouponStatus {
    ASSIGNED("지급"),USED("사용"),CANCELED("취소"),EXPIRED("만료");
    private String desc;

    CouponStatus(String desc) {
        this.desc = desc;
    }

}
