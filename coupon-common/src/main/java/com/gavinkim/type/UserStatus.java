package com.gavinkim.type;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public enum UserStatus {
    ACTIVATE("활성"),WAIT("대기"),BLOCK("BLOCK"),STOP("휴면");
    private String desc;
    UserStatus(String desc) {
        this.desc = desc;
    }

}
