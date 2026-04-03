package com.atria.deskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInResponse {

    private String stayId;
    private String folioId;

    private String guestId;
    private String guestName;

    private String roomId;
    private String roomNumber;
    private String roomType;

    private Instant checkInTime;

    private String status; // ACTIVE

    public CheckInResponse(String stayId, String folioId) {
        this.stayId = stayId;
        this.folioId = folioId;
    }
}