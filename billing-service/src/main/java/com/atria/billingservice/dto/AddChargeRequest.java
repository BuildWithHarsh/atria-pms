package com.atria.billingservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddChargeRequest {

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotBlank
    private String source; 
    // FNB, LAUNDRY, ROOM, SPA

    @NotBlank
    private String referenceId;

    private String description;

    private Integer quantity = 1;

    // Future-ready
    private BigDecimal taxAmount;
}