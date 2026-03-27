package com.atria.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String username;
    @JsonIgnore
    private String firstName;
    @JsonIgnore
    private String lastName;
    @JsonIgnore
    private String image;
    @JsonIgnore
    private boolean enabled;
    @JsonIgnore
    private String provider;
    private List roles;
}
