package com.gavinkim.controller.user;

import com.gavinkim.config.JwtTokenHelper;
import com.gavinkim.dto.SignInRequestDto;
import com.gavinkim.dto.SignInResponseDto;
import com.gavinkim.model.user.User;
import com.gavinkim.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SignInResource {

    @Value("${jwt.token.expiredhours}")
    private int expiredHours;

    private final JwtTokenHelper jwtTokenHelper;
    private final UserService userService;

    @Autowired
    public SignInResource(JwtTokenHelper jwtTokenHelper,
                          UserService userService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userService = userService;
    }

    public SignInResponseDto signin(SignInRequestDto signInRequestDto){
        User user = userService.signIn(signInRequestDto);
        LocalDateTime expiredAt = LocalDateTime.now().plusHours(expiredHours);
        return SignInResponseDto.builder()
                .token(jwtTokenHelper.generateToken(user.getEmail(), expiredAt))
                .expiredAt(expiredAt)
                .build();
    }


}
