package com.atria.billingservice.mapper;

import com.atria.billingservice.dto.*;
import com.atria.billingservice.entity.Folio;
import com.atria.billingservice.entity.FolioItem;
import com.atria.billingservice.entity.Payment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class FolioMapper {

    // -------- Folio → Response --------

    public FolioResponse toFolioResponse(Folio folio) {
        FolioResponse res = new FolioResponse();
        res.setFolioId(folio.getId());
        res.setCustomerId(folio.getCustomerId());
        res.setStatus(folio.getStatus().name());
        res.setCurrency(folio.getCurrency());
        res.setTotalCharges(folio.getTotalCharges());
        res.setTotalPayments(folio.getTotalPayments());
        res.setBalance(folio.getBalance());
        return res;
    }

    // -------- Folio + Items → Detail Response --------

    public FolioDetailResponse toFolioDetailResponse(Folio folio, List<FolioItem> items) {

        List<FolioItemResponse> itemResponses = items.stream()
                .map(this::toFolioItemResponse)
                .toList();

        FolioDetailResponse res = new FolioDetailResponse();
        res.setFolioId(folio.getId());
        res.setCustomerId(folio.getCustomerId());
        res.setStatus(folio.getStatus().name());
        res.setTotalCharges(folio.getTotalCharges());
        res.setTotalPayments(folio.getTotalPayments());
        res.setBalance(folio.getBalance());
        res.setItems(itemResponses);

        return res;
    }

    // -------- FolioItem → DTO --------

    public FolioItemResponse toFolioItemResponse(FolioItem item) {
        FolioItemResponse r = new FolioItemResponse();
        r.setId(item.getId());
        r.setType(item.getType().name());
        r.setAmount(item.getAmount());
        r.setSource(item.getSource());
        r.setReferenceId(item.getReferenceId());
        r.setDescription(item.getDescription());
        r.setQuantity(item.getQuantity());
        r.setTaxAmount(item.getTaxAmount());
        return r;
    }

    // -------- Charge Response --------

    public ChargeResponse toChargeResponse(FolioItem item, BigDecimal balance) {
        ChargeResponse res = new ChargeResponse();
        res.setFolioItemId(item.getId());
        res.setFolioId(item.getFolioId());
        res.setAmount(item.getAmount());
        res.setStatus("SUCCESS");
        res.setUpdatedBalance(balance);
        return res;
    }

    public ChargeResponse toDuplicateChargeResponse(String folioId, BigDecimal amount) {
        ChargeResponse res = new ChargeResponse();
        res.setFolioId(folioId);
        res.setAmount(amount);
        res.setStatus("DUPLICATE");
        return res;
    }

    // -------- Payment Response --------

    public PaymentResponse toPaymentResponse(Payment payment, BigDecimal balance) {
        PaymentResponse res = new PaymentResponse();
        res.setPaymentId(payment.getId());
        res.setFolioItemId(payment.getFolioItemId());
        res.setAmount(payment.getAmount());
        res.setPaymentType(payment.getPaymentType().name());
        res.setMethod(payment.getMethod().name());
        res.setStatus(payment.getStatus().name());
        res.setUpdatedBalance(balance);
        return res;
    }
}