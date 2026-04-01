package com.atria.billingservice.controller;

import com.atria.billingservice.dto.*;
import com.atria.billingservice.service.FolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/folios")
@RequiredArgsConstructor
public class FolioController {

    private final FolioService folioService;

    // ---------------- CREATE FOLIO ----------------

    @PostMapping
    public ResponseEntity<BillingResponse<FolioResponse>> createFolio(
            @Valid @RequestBody CreateFolioRequest request,
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestHeader("X-User-Id") String userId
    ) {

        FolioResponse response = folioService.createFolio(request, tenantId, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BillingResponse<>("SUCCESS", "Folio created successfully", response));
    }

    // ---------------- ADD CHARGE ----------------

    @PostMapping("/{folioId}/charges")
    public ResponseEntity<BillingResponse<ChargeResponse>> addCharge(
            @PathVariable String folioId,
            @Valid @RequestBody AddChargeRequest request,
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestHeader("X-User-Id") String userId
    ) {

        ChargeResponse response =
                folioService.addCharge(folioId, request, tenantId, userId);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Charge added successfully", response)
        );
    }

    // ---------------- ADD PAYMENT ----------------

    @PostMapping("/{folioId}/payments")
    public ResponseEntity<BillingResponse<PaymentResponse>> addPayment(
            @PathVariable String folioId,
            @Valid @RequestBody AddPaymentRequest request,
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestHeader("X-User-Id") String userId
    ) {

        PaymentResponse response =
                folioService.addPayment(folioId, request, tenantId, userId);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Payment added successfully", response)
        );
    }

    // ---------------- GET FOLIO DETAILS ----------------

    @GetMapping("/{folioId}")
    public ResponseEntity<BillingResponse<FolioDetailResponse>> getFolio(
            @PathVariable String folioId,
            @RequestHeader("X-Tenant-Id") String tenantId
    ) {

        FolioDetailResponse response =
                folioService.getFolio(folioId, tenantId);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Folio fetched successfully", response)
        );
    }

    // ---------------- CLOSE FOLIO  ----------------
    @PostMapping("/{folioId}/close")
    public ResponseEntity<BillingResponse<CloseFolioResponse>> closeFolio(
            @PathVariable String folioId,
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestHeader("X-User-Id") String userId
    ) {

        CloseFolioResponse response =
                folioService.closeFolio(folioId, tenantId, userId);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Folio closed successfully", response)
        );
    }


    // ---------------- REVERSE FOLIO ITEM  ----------------
    @PostMapping("/{folioId}/reverse")
    public ResponseEntity<BillingResponse<ReverseChargeResponse>> reverseCharge(
            @PathVariable String folioId,
            @Valid @RequestBody ReverseChargeRequest request,
            @RequestHeader("X-Tenant-Id") String tenantId,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Role") String userRole
    ) {

        ReverseChargeResponse response =
                folioService.reverseCharge(folioId, request, tenantId, userId, userRole);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Charge reversed successfully", response)
        );
    }
}