package com.atria.billingservice.service;

import com.atria.billingservice.dto.InvoiceResponse;

public interface InvoiceService {

    InvoiceResponse getInvoice(String folioId, String tenantId);
}