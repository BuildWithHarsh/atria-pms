package com.atria.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private String paymentId;
    private String folioItemId;
    private String status;
    private String paymentType;
    private String method;
    private BigDecimal amount;
    private BigDecimal updatedBalance;
}