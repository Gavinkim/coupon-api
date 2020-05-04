package com.gavinkim.controller.auth;

import com.gavinkim.dto.SignInRequestDto;
import com.gavinkim.dto.SignInResponseDto;
import com.gavinkim.dto.SignUpRequestDto;
import com.gavinkim.model.user.UserService;
import com.gavinkim.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SignInResource {

    @Value("${jwt.token.expiredhours}")
    private int expiredHours;

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignInResource(AuthenticationManager authenticationManager
            ,TokenProvider tokenProvider
            ,UserService userService
            ,PasswordEncoder passwordEncoder) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public SignInResponseDto signin(SignInRequestDto signInRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequestDto.getEmail(), signInRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LocalDateTime expiredAt = LocalDateTime.now().plusHours(expiredHours);
        String token = tokenProvider.generateToken(authentication,expiredAt);
        return SignInResponseDto.builder()
                .token(token)
                .expiredAt(expiredAt)
                .build();
    }

    public void signUp(SignUpRequestDto signUpRequestDto){
        String encryptPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        signUpRequestDto.setPassword(encryptPassword);
        userService.signUp(signUpRequestDto);
    }


}
