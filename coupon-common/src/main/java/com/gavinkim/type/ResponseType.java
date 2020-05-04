package com.gavinkim.type;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ResponseType {
    SUCCESS("C2000","SUCCESS")
    ,ERROR("C4000","SYSTEM ERROR")
    ,COUPON_NOTFOUND("C4001","존재하지 않거나, 유효하지 않은 쿠폰 입니다.")
    ,USER_NOTFOUND("C4002","사용자를 찾을 수 없습니다.")
    ,ALREADY("C4003","중복 요청 되었습니다.")
    ,NOT_AVAILABLE("C4004","사용 가능한 쿠폰이 존재 하지 않습니다")
    ,VALIDATION("C405","validation fail")
    ,SIGN_IN("C4006","login error")
    ,INVALID_TOKEN("C4007","Invalid token")
    ,UNAUTHORIZED("C4008","인증 실패 하였습니다.");

    private String code;
    @Setter
    private String message;

    ResponseType(String code,String message) {
        this.message = message;
        this.code = code;
    }
}
