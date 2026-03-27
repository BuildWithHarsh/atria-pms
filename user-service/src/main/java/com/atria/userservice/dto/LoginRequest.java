package com.atria.userservice.dto;

public record LoginRequest(
        String username,
        String password
) {
}
