package com.atria.deskservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends BaseEntity  {
    @Id
    private String id;
    private String tenantId;
    private String guestId;
    private String roomTypeId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
    private BigDecimal advanceAmount;
    private int roomCount; // number of rooms booked
    private Instant createdAt;
}