package com.gavinkim.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
public class SignInResponseDto {
    private String token;
    private LocalDateTime expiredAt;

    @Builder
    public SignInResponseDto(String token, LocalDateTime expiredAt) {
        this.token = token;
        this.expiredAt = expiredAt;
    }
}
