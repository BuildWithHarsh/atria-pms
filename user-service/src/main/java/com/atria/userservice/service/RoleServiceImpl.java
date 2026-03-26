package com.atria.userservice.service;

import com.atria.userservice.dto.RoleDTO;
import com.atria.userservice.entity.Role;
import com.atria.userservice.mapper.RoleMapper;
import com.atria.userservice.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleDTO createRole(RoleDTO dto) {

        if (roleRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Role already exists");
        }

        Role role = RoleMapper.toEntity(dto);

        Role savedRole = roleRepository.save(role);

        return RoleMapper.toDTO(savedRole);
    }

    @Override
    public RoleDTO getRoleById(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return RoleMapper.toDTO(role);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO updateRole(UUID id, RoleDTO dto) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setName(dto.getName());

        Role updatedRole = roleRepository.save(role);

        return RoleMapper.toDTO(updatedRole);
    }

    @Override
    public void deleteRole(UUID id) {

        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role not found");
        }

        roleRepository.deleteById(id);
    }
}