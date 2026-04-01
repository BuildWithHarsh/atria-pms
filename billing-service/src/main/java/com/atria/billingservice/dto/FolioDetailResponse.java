package com.atria.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolioDetailResponse {

    private String folioId;
    private String customerId;
    private String status;

    private BigDecimal totalCharges;
    private BigDecimal totalPayments;
    private BigDecimal balance;

    private List<FolioItemResponse> items;
}