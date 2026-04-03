package com.atria.deskservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room extends BaseEntity  {
    @Id
    private String id;
    private String tenantId;
    private String roomNumber;
    private String roomTypeId;
    private String status; // AVAILABLE, OCCUPIED
    private int floor;
    private int capacity;
}