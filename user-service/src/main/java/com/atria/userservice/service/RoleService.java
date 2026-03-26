package com.atria.userservice.service;

import com.atria.userservice.dto.RoleDTO;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    RoleDTO createRole(RoleDTO dto);

    RoleDTO getRoleById(UUID id);

    List<RoleDTO> getAllRoles();

    RoleDTO updateRole(UUID id, RoleDTO dto);

    void deleteRole(UUID id);
}