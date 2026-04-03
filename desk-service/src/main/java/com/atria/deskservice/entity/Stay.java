package com.atria.deskservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stay extends BaseEntity  {
    @Id
    private String id;
    private String tenantId;
    private String guestId;
    private String roomId;
    private String folioId;
    private Instant checkInTime;
    private LocalDate expectedCheckoutDate;
    private String status; // ACTIVE / COMPLETED
}