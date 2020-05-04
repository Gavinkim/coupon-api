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
@ApiModel(value = "[SignUpDto] - 회원가입 정보")
public class SignUpRequestDto {

    @NotNull(message = "please input username")
    @NotEmpty(message = "Please input username")
    @Size(min = 4,max = 30,message = "username 은 4 ~ 30 자여야 합니다.")
    private String username;

    @NotNull(message = "please input password")
    @NotEmpty(message = "Please input password")
    @Size(min = 4,message = "password 는 4 이상이여야 합니다.")
    private String password;

    @NotNull(message = "please input email")
    @NotEmpty(message = "Please input email")
    @Email(message = "Please check email format")
    private String email;
}
