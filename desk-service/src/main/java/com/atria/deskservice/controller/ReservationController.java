package com.atria.deskservice.controller;

import com.atria.deskservice.dto.BillingResponse;
import com.atria.deskservice.dto.CreateReservationRequest;
import com.atria.deskservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<BillingResponse<String>> createReservation(
            @RequestBody CreateReservationRequest request,
            @RequestHeader("X-Tenant-Id") String tenantId
    ) {

        String reservationId =
                reservationService.createReservation(request, tenantId);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Reservation created successfully", reservationId)
        );
    }
}