package com.atria.deskservice.service;

import com.atria.deskservice.dto.CreateReservationRequest;

public interface ReservationService {

    String createReservation(CreateReservationRequest request, String tenantId);
}