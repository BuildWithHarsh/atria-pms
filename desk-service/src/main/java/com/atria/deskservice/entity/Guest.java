package com.atria.deskservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guest extends BaseEntity {
    @Id
    private String id;
    private String tenantId;
    private String name;
    private String phone;
    private String email;
    private String idProofType;
    private String idProofNumber;
}