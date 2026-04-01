package com.atria.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolioItemResponse {

    private String id;
    private String type; // CHARGE / PAYMENT
    private BigDecimal amount;
    private String source;
    private String referenceId;

    private String description;
    private Integer quantity;

    private BigDecimal taxAmount;

    private LocalDateTime createdAt;
}