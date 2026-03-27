package com.atria.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    @NotBlank
    @Email
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String image;
    private String provider;
    private Set<String> roles; // role names like "ADMIN", "USER"
}