package com.atria.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItem {

    private String type;
    private String source;
    private BigDecimal amount;
    private String description;
    private Instant createdAt;
    private String method;
    private String paymentType;
}