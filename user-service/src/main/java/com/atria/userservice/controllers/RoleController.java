package com.atria.userservice.controllers;

import com.atria.userservice.dto.RoleDto;
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
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody RoleDto dto) {
        return new ResponseEntity<>(IRoleService.createRole(dto), HttpStatus.CREATED);
    }

    // ✅ Get Role by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(IRoleService.getRoleById(id));
    }

    // ✅ Get All Roles
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(IRoleService.getAllRoles());
    }

    // ✅ Update Role
    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleDto dto) {

        return ResponseEntity.ok(IRoleService.updateRole(id, dto));
    }

    // ✅ Delete Role
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        IRoleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}