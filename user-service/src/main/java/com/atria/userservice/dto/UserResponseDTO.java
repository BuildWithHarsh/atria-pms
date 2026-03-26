package com.atria.userservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
   private String status;
   private String message;
   private UserResponseObject user;
}

