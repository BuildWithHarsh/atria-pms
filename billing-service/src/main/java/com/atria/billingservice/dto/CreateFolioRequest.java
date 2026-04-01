package com.atria.billingservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

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