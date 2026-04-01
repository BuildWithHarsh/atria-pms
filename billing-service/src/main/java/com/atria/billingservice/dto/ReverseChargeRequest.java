package com.atria.billingservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReverseChargeRequest {

    @NotBlank
    private String referenceId;

    private String reason;
}