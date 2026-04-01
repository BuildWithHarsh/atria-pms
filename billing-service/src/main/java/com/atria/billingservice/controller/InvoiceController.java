package com.atria.billingservice.controller;

import com.atria.billingservice.dto.BillingResponse;
import com.atria.billingservice.dto.InvoiceResponse;
import com.atria.billingservice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/{folioId}")
    public ResponseEntity<BillingResponse<InvoiceResponse>> getInvoice(
            @PathVariable String folioId,
            @RequestHeader("X-Tenant-Id") String tenantId
    ) {

        InvoiceResponse response =
                invoiceService.getInvoice(folioId, tenantId);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Invoice fetched successfully", response)
        );
    }
}