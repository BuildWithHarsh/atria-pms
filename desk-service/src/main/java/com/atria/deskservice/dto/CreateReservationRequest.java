package com.atria.deskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationRequest {
    private String guestId;
    private String roomTypeId;
    private LocalDate checkInDate;
    private int roomCount;
    private LocalDate checkOutDate;
    private BigDecimal advanceAmount;
}