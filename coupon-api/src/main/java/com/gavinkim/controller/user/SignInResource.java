package com.gavinkim.controller.user;

import com.gavinkim.config.JwtTokenHelper;
import com.gavinkim.dto.SignInRequestDto;
import com.gavinkim.dto.SignInResponseDto;
import com.gavinkim.model.user.User;
import com.gavinkim.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SignInResource {

    private final JwtTokenHelper jwtTokenHelper;
    private final UserService userService;

    @Autowired
    public SignInResource(JwtTokenHelper jwtTokenHelper,
                          UserService userService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userService = userService;
    }

    SignInResponseDto signin(SignInRequestDto signInRequestDto){
        User user = userService.signIn(signInRequestDto);
        LocalDateTime now = LocalDateTime.now();
        return SignInResponseDto.builder()
                .token(jwtTokenHelper.generateToken(user.getEmail(), now))
                .expiredAt(now.plusHours(3))
                .build();
    }


}
