package com.atria.deskservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFolioRequest {

    @NotBlank
    private String customerId;

    // Optional: booking reference from Atria Desk
    private String referenceId;

    private String currency = "INR";
}