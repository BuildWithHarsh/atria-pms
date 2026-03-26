package com.atria.userservice.service;

import com.atria.userservice.dto.RoleDTO;

import java.util.List;

public interface IRoleService {

    RoleDTO createRole(RoleDTO dto);

    RoleDTO getRoleById(Long id);

    List<RoleDTO> getAllRoles();

    RoleDTO updateRole(Long id, RoleDTO dto);

    void deleteRole(Long id);
}