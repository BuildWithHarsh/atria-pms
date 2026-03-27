package com.atria.userservice.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto<T> {
    private String status;
    private String message;
    @JsonIgnore
    private String accessToken;
    @JsonIgnore
    private String refreshToken;
    @JsonIgnore
    private String tokenType; // Bearer

    private T user;
}
