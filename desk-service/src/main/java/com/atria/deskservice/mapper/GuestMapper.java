package com.atria.deskservice.mapper;

import com.atria.deskservice.dto.CreateGuestRequest;
import com.atria.deskservice.dto.GuestResponse;
import com.atria.deskservice.entity.Guest;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class GuestMapper {

    public Guest toEntity(CreateGuestRequest req, String tenantId) {
        Guest guest = new Guest();
        guest.setId(UUID.randomUUID().toString());
        guest.setTenantId(tenantId);
        guest.setName(req.getName());
        guest.setPhone(req.getPhone());
        guest.setEmail(req.getEmail());
        guest.setIdProofType(req.getIdProofType());
        guest.setIdProofNumber(req.getIdProofNumber());
        return guest;
    }

    public GuestResponse toDto(Guest guest) {

        GuestResponse dto = new GuestResponse();

        dto.setGuestId(guest.getId());
        dto.setName(guest.getName());
        dto.setPhone(guest.getPhone());
        dto.setEmail(guest.getEmail());
        dto.setIdProofType(guest.getIdProofType());
        dto.setIdProofNumber(guest.getIdProofNumber());
        dto.setCreatedAt(guest.getCreatedAt());

        return dto;
    }
}