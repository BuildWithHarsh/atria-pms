package com.atria.deskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @AllArgsConstructor @NoArgsConstructor
public class ReservationResponse {

    private String id;
    private String reservationId;
    private String guestId;
    private String roomTypeId;

    private int roomCount;

    private String advanceAmount;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private String status;
}