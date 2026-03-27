package com.atria.userservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseDto<T> {
   private String status;
   private String message;
   private T user;
}

