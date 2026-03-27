package com.atria.userservice.service;

import com.atria.userservice.dto.RoleDto;

import java.util.List;

public interface IRoleService {

    RoleDto createRole(RoleDto dto);

    RoleDto getRoleById(Long id);

    List<RoleDto> getAllRoles();

    RoleDto updateRole(Long id, RoleDto dto);

    void deleteRole(Long id);
}