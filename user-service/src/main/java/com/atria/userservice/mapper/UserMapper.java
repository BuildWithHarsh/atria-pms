package com.atria.userservice.mapper;

import com.atria.userservice.dto.UserRequestDTO;
import com.atria.userservice.dto.UserResponseDTO;
import com.atria.userservice.dto.UserResponseObject;
import com.atria.userservice.entity.Provider;
import com.atria.userservice.entity.Role;
import com.atria.userservice.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    // ✅ Entity → Response DTO
    public static UserResponseObject toDTO(User user) {
        if (user == null) return null;

        return UserResponseObject.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .image(user.getImage())
                .enabled(user.isEnabled())
                .provider(user.getProvider().name())
                .roles(user.getRoles()
                        .stream()
                        .map(RoleMapper::toDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    // ✅ Request DTO → Entity (basic fields only)
    public static User toEntity(UserRequestDTO dto) {
        if (dto == null) return null;

        return User.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .password(dto.getPassword()) // ⚠️ encode before saving
                .image(dto.getImage())
                .provider(Provider.valueOf(dto.getProvider()))
                .enabled(true)
                .build();
    }

    // ✅ Set roles after fetching from DB
    public static void mapRoles(User user, Set<Role> roles) {
        user.setRoles(roles);
    }
}