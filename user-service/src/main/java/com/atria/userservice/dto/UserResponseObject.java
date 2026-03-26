package com.atria.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseObject {
    private Long id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String image;
    private boolean enabled;
    private String provider;
    private Set<RoleDTO> roles;
}
