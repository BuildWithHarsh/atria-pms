package com.atria.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolioResponse {

    private String folioId;
    private String customerId;
    private String status;
    private String currency;

    private BigDecimal totalCharges;
    private BigDecimal totalPayments;
    private BigDecimal balance;

    private LocalDateTime createdAt;
}