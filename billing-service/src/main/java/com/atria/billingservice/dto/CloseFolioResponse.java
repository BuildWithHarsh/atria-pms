package com.atria.billingservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
public class CloseFolioResponse {

    private String folioId;
    private String billNumber;
    private String status;
    private LocalDateTime closedAt;
}