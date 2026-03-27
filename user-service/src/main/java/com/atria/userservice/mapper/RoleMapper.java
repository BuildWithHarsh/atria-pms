package com.atria.userservice.mapper;

import com.atria.userservice.dto.RoleDto;
import com.atria.userservice.entity.Role;

public class RoleMapper {

    public static RoleDto toDTO(Role role) {
        if (role == null) return null;

        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static Role toEntity(RoleDto dto) {
        if (dto == null) return null;

        return Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}