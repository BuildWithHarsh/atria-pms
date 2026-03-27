package com.atria.userservice.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        Long expiresIn,
        String tokenType,
        UserResponseDto user

) {
}
