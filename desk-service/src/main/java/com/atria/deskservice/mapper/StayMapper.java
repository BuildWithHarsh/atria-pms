package com.atria.deskservice.mapper;

import com.atria.deskservice.dto.CheckInRequest;
import com.atria.deskservice.dto.StayResponse;
import com.atria.deskservice.entity.Stay;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class StayMapper {

    public Stay toEntity(CheckInRequest req,
                         String tenantId,
                         String folioId) {

        Stay stay = new Stay();
        stay.setId(UUID.randomUUID().toString());
        stay.setTenantId(tenantId);
        stay.setGuestId(req.getGuestId());
        stay.setRoomId(req.getRoomId());
        stay.setFolioId(folioId);
        stay.setCheckInTime(Instant.now());
        stay.setExpectedCheckoutDate(req.getExpectedCheckoutDate());
        stay.setStatus("ACTIVE");

        return stay;
    }

    public StayResponse toDto(Stay stay) {

        StayResponse dto = new StayResponse();
        dto.setGuestId(stay.getGuestId());
        dto.setRoomId(stay.getRoomId());
        dto.setFolioId(stay.getFolioId());
        dto.setCheckInTime(stay.getCheckInTime());
        dto.setExpectedCheckoutDate(stay.getExpectedCheckoutDate());
        dto.setStatus(stay.getStatus());

        return dto;
    }
}