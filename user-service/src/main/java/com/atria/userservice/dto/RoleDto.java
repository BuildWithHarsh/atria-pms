package com.atria.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {
    private Long id;
    @NotBlank
    private String name;
}