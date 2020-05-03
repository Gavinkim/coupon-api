package com.gavinkim.dto;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "[SignUpDto] - 회원가입 정보")
public class SignUpRequestDto {

    private String id;
    private String password;
}
