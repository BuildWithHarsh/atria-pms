package com.atria.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReverseChargeResponse {

    private String folioItemId;
    private String reversedReferenceId;
    private BigDecimal amount;
    private BigDecimal updatedBalance;
}