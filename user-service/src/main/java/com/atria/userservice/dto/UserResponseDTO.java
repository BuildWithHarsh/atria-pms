package com.atria.userservice.dto;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private UUID id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String image;
    private boolean enabled;
    private String provider;
    private Set<RoleDTO> roles;
}