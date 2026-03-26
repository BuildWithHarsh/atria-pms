package com.atria.userservice.controllers;

import com.atria.userservice.dto.RoleDTO;
import com.atria.userservice.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // ✅ Create Role
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO dto) {
        return new ResponseEntity<>(roleService.createRole(dto), HttpStatus.CREATED);
    }

    // ✅ Get Role by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable UUID id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    // ✅ Get All Roles
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    // ✅ Update Role
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(
            @PathVariable UUID id,
            @Valid @RequestBody RoleDTO dto) {

        return ResponseEntity.ok(roleService.updateRole(id, dto));
    }

    // ✅ Delete Role
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}