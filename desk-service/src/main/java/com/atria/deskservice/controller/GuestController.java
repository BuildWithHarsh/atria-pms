package com.atria.deskservice.controller;

import com.atria.deskservice.dto.BillingResponse;
import com.atria.deskservice.dto.CreateGuestRequest;
import com.atria.deskservice.dto.GuestResponse;
import com.atria.deskservice.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<BillingResponse<GuestResponse>> createGuest(
            @RequestBody CreateGuestRequest request,
            @RequestHeader("X-Tenant-Id") String tenantId
    ) {

        GuestResponse guest = guestService.createGuest(request, tenantId);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Guest created successfully", guest)
        );
    }
}