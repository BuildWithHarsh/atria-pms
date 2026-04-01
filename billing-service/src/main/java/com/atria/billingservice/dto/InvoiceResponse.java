package com.atria.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {

    private String folioId;
    private String billNumber;
    private String customerId;

    private InvoiceSummary summary;

    private BigDecimal totalCharges;
    private BigDecimal totalPayments;
    private BigDecimal balance;

    private List<InvoiceItem> items;
}