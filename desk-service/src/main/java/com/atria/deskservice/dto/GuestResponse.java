package com.atria.deskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data @AllArgsConstructor @NoArgsConstructor
public class GuestResponse {
    private String guestId;
    private String name;
    private String phone;
    private String email;
    private String idProofType;
    private String idProofNumber;
}