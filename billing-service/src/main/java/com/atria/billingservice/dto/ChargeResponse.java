package com.atria.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargeResponse {

    private String folioItemId;
    private String folioId;
    private BigDecimal amount;
    private String status; // SUCCESS / DUPLICATE

    private BigDecimal updatedBalance;
}