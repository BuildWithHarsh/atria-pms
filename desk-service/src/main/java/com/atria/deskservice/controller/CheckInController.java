package com.atria.deskservice.controller;

import com.atria.deskservice.dto.BillingResponse;
import com.atria.deskservice.dto.CheckInRequest;
import com.atria.deskservice.dto.CheckInResponse;
import com.atria.deskservice.service.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/check-in")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @PostMapping
    public ResponseEntity<BillingResponse<CheckInResponse>> checkIn(
            @RequestBody CheckInRequest request,
            @RequestHeader("X-Tenant-Id") String tenantId
    ) {

        CheckInResponse response =
                checkInService.checkIn(request, tenantId);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Check-in successful", response)
        );
    }
}