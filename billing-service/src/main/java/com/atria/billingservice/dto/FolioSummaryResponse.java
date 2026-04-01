package com.atria.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolioSummaryResponse {

    private String folioId;
    private BigDecimal totalCharges;
    private BigDecimal totalPayments;
    private BigDecimal balance;
}