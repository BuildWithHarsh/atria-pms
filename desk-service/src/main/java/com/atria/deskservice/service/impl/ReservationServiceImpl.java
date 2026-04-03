package com.atria.deskservice.service.impl;

import com.atria.deskservice.dto.CreateReservationRequest;
import com.atria.deskservice.entity.Reservation;
import com.atria.deskservice.mapper.ReservationMapper;
import com.atria.deskservice.repository.ReservationRepository;
import com.atria.deskservice.repository.RoomRepository;
import com.atria.deskservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final RoomRepository roomRepository;

    @Override
    public String createReservation(CreateReservationRequest request, String tenantId) {

        // 1. Check availability
        int totalRooms = roomRepository.getTotalRooms(
                request.getRoomTypeId()
        );

        int reservedRooms = reservationRepository.getReservedRoomCount(
                request.getRoomTypeId(),
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        int availableRooms = totalRooms - reservedRooms;

        if (availableRooms < request.getRoomCount()) {
            throw new RuntimeException("Not enough rooms available");
        }

        /*int overlapping = reservationRepository.countOverlappingReservations(
                request.getRoomTypeId(),
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        if (availableRooms - overlapping <= 0) {
            throw new RuntimeException("No rooms available for selected dates");
        }*/

        // 2. Create reservation
        Reservation reservation = reservationMapper.toEntity(request, tenantId);

        reservationRepository.save(reservation);

        return reservation.getId();
    }
}