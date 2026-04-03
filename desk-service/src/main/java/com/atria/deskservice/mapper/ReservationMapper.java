package com.atria.deskservice.mapper;

import com.atria.deskservice.dto.CreateReservationRequest;
import com.atria.deskservice.dto.ReservationResponse;
import com.atria.deskservice.entity.Reservation;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class ReservationMapper {

    public Reservation toEntity(CreateReservationRequest req, String tenantId) {

        Reservation res = new Reservation();
        res.setId(UUID.randomUUID().toString());
        res.setTenantId(tenantId);
        res.setGuestId(req.getGuestId());
        res.setRoomTypeId(req.getRoomTypeId());
        res.setCheckInDate(req.getCheckInDate());
        res.setCheckOutDate(req.getCheckOutDate());
        res.setRoomCount(req.getRoomCount());
        res.setAdvanceAmount(req.getAdvanceAmount());
        res.setStatus("BOOKED");
        res.setCreatedAt(Instant.now());

        return res;
    }

    public ReservationResponse toDto(Reservation res) {

        ReservationResponse dto = new ReservationResponse();

        dto.setId(res.getId());
        dto.setGuestId(res.getGuestId());
        dto.setRoomTypeId(res.getRoomTypeId());
        dto.setRoomCount(res.getRoomCount());
        dto.setCheckInDate(res.getCheckInDate());
        dto.setCheckOutDate(res.getCheckOutDate());
        dto.setStatus(res.getStatus());
        dto.setAdvanceAmount(res.getAdvanceAmount().toString());

        return dto;
    }
}