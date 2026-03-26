package com.atria.userservice.controllers;

import com.atria.userservice.dto.RoleDTO;
import com.atria.userservice.service.IRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService IRoleService;

    // ✅ Create Role
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO dto) {
        return new ResponseEntity<>(IRoleService.createRole(dto), HttpStatus.CREATED);
    }

    // ✅ Get Role by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(IRoleService.getRoleById(id));
    }

    // ✅ Get All Roles
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(IRoleService.getAllRoles());
    }

    // ✅ Update Role
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleDTO dto) {

        return ResponseEntity.ok(IRoleService.updateRole(id, dto));
    }

    // ✅ Delete Role
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        IRoleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}