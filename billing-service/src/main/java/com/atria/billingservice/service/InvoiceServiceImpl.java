package com.atria.billingservice.service;

import com.atria.billingservice.dto.InvoiceItem;
import com.atria.billingservice.dto.InvoiceResponse;
import com.atria.billingservice.dto.InvoiceSummary;
import com.atria.billingservice.entity.*;
import com.atria.billingservice.exception.FolioNotFoundException;
import com.atria.billingservice.repository.FolioItemRepository;
import com.atria.billingservice.repository.FolioRepository;
import com.atria.billingservice.repository.PaymentRepository;
import com.atria.billingservice.service.InvoiceService;
import com.atria.billingservice.validation.InvoiceValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final FolioRepository folioRepository;
    private final FolioItemRepository folioItemRepository;
    private final PaymentRepository paymentRepository;
    private final InvoiceValidationService invoiceValidationService;

    @Override
    public InvoiceResponse getInvoice(String folioId, String tenantId) {

        // 1. Fetch folio
        Folio folio = folioRepository.findByIdAndTenantId(folioId, tenantId)
                .orElseThrow(() ->
                        new FolioNotFoundException("Folio not found: " + folioId));

        // 2. Fetch all items
        List<FolioItem> items = folioItemRepository
                .findByFolioIdAndTenantIdOrderByCreatedAtAsc(folioId, tenantId);

        invoiceValidationService.validateInvoiceGeneration(folio, items);
        // 3. Prepare summary
        InvoiceSummary summary = new InvoiceSummary();

        List<InvoiceItem> chargeItems = new ArrayList<>();
        List<InvoiceItem> paymentItems = new ArrayList<>();


        for (FolioItem item : items) {

            InvoiceItem dto = new InvoiceItem();
            dto.setType(item.getType().name());
            dto.setSource(item.getSource());
            dto.setAmount(item.getAmount());
            dto.setDescription(item.getDescription());
            dto.setCreatedAt(item.getCreatedAt());

            // ---------------- HANDLE PAYMENTS ----------------
            if (item.getType() == FolioItemType.PAYMENT) {

                Payment payment = paymentRepository
                        .findByFolioItemId(item.getId())
                        .orElse(null);

                if (payment != null) {
                    dto.setMethod(payment.getMethod().name());
                    dto.setPaymentType(payment.getPaymentType().name());
                }

            } else {
                // ---------------- HANDLE CHARGES / REVERSALS ----------------

                BigDecimal amt = item.getAmount();

                switch (item.getSource()) {
                    case "ROOM" ->
                            summary.setRoomCharges(summary.getRoomCharges().add(amt));

                    case "FNB" ->
                            summary.setFoodCharges(summary.getFoodCharges().add(amt));

                    case "LAUNDRY" ->
                            summary.setLaundryCharges(summary.getLaundryCharges().add(amt));

                    default ->
                            summary.setOtherCharges(summary.getOtherCharges().add(amt));
                }
            }

            if(dto.getType().equals(FolioItemType.PAYMENT.toString())) {
                paymentItems.add(dto);
            }else  {
                chargeItems.add(dto);
            }
        }

        // 4. Build response
        InvoiceResponse response = new InvoiceResponse();
        response.setFolioId(folio.getId());
        response.setBillNumber(folio.getBillNumber());
        response.setCustomerId(folio.getCustomerId());
        if (folio.getStatus() == FolioStatus.CLOSED) {
            response.setInvoiceType("FINAL");
        } else {
            response.setInvoiceType("PROFORMA");
        }
        response.setSummary(summary);
        response.setTotalCharges(folio.getTotalCharges());
        response.setTotalPayments(folio.getTotalPayments());
        response.setBalance(folio.getBalance());
        response.setChargeItems(chargeItems);
        response.setPaymentItems(paymentItems);

        return response;
    }
}