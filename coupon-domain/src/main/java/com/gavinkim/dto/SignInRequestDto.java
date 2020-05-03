package com.gavinkim.dto;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "[SignInDto] - 로그인 정보")
public class SignInRequestDto {

    private String id;
    private String password;
}
