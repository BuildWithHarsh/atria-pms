package com.atria.deskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data @AllArgsConstructor
@NoArgsConstructor
public class StayResponse {

    private String stayId;
    private String guestId;
    private String roomId;
    private String folioId;

    private Instant checkInTime;
    private LocalDate expectedCheckoutDate;

    private String status;
}