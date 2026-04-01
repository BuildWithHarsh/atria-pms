package com.atria.billingservice.service;

import com.atria.billingservice.dto.*;
import jakarta.transaction.Transactional;

public interface FolioService {

    FolioResponse createFolio(CreateFolioRequest request, String tenantId, String userId);

    ChargeResponse addCharge(String folioId, AddChargeRequest request, String tenantId, String userId);

    PaymentResponse addPayment(String folioId, AddPaymentRequest request, String tenantId, String userId);

    FolioDetailResponse getFolio(String folioId, String tenantId);

    CloseFolioResponse closeFolio(String folioId, String tenantId, String userId);

    ReverseChargeResponse reverseCharge(String folioId, ReverseChargeRequest request, String tenantId, String userId, String userRole);
}