package com.atria.userservice.service.impl;

import com.atria.userservice.dto.RoleDto;
import com.atria.userservice.entity.Role;
import com.atria.userservice.mapper.RoleMapper;
import com.atria.userservice.repositories.RoleRepository;
import com.atria.userservice.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleDto createRole(RoleDto dto) {

        if (roleRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Role already exists");
        }

        Role role = RoleMapper.toEntity(dto);

        Role savedRole = roleRepository.save(role);

        return RoleMapper.toDTO(savedRole);
    }

    @Override
    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return RoleMapper.toDTO(role);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto updateRole(Long id, RoleDto dto) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setName(dto.getName());

        Role updatedRole = roleRepository.save(role);

        return RoleMapper.toDTO(updatedRole);
    }

    @Override
    public void deleteRole(Long id) {

        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found");
        }

        roleRepository.deleteById(id);
    }
}