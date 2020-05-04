package com.gavinkim.dto;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@ApiModel(value = "[SignInDto] - 로그인 정보")
public class SignInRequestDto {

    @NotNull(message = "please input username")
    @NotEmpty(message = "Please input username")
    @Email
    private String email;

    @NotNull(message = "please input password")
    @NotEmpty(message = "Please input password")
    @Size(min = 4,message = "password 는 4 이상이여야 합니다.")
    private String password;


}
