package com.gavinkim.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SignInResponseDto {
    private String token;
    private String tokenType = "Bearer";
    private LocalDateTime expiredAt;

    @Builder
    public SignInResponseDto(String token, LocalDateTime expiredAt) {
        this.token = token;
        this.expiredAt = expiredAt;
    }
}
