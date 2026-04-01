package com.atria.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceSummary {

    private BigDecimal roomCharges = BigDecimal.ZERO;
    private BigDecimal foodCharges = BigDecimal.ZERO;
    private BigDecimal laundryCharges = BigDecimal.ZERO;
    private BigDecimal otherCharges = BigDecimal.ZERO;
}