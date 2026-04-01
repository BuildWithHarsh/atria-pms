package com.atria.billingservice.service;

import com.atria.billingservice.constant.ExceptionMessageConstants;
import com.atria.billingservice.dto.*;
import com.atria.billingservice.entity.*;
import com.atria.billingservice.exception.DuplicateChargeException;
import com.atria.billingservice.exception.DuplicateTransactionException;
import com.atria.billingservice.exception.FolioNotFoundException;
import com.atria.billingservice.exception.InvalidFolioStateException;
import com.atria.billingservice.helper.BillNumberGenerator;
import com.atria.billingservice.mapper.FolioMapper;
import com.atria.billingservice.repository.FolioItemRepository;
import com.atria.billingservice.repository.FolioRepository;
import com.atria.billingservice.repository.PaymentRepository;
import com.atria.billingservice.validation.FolioValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FolioServiceImpl implements FolioService {

    private final FolioRepository folioRepository;
    private final FolioItemRepository folioItemRepository;
    private final PaymentRepository paymentRepository;
    private final FolioMapper folioMapper;
    private final BillNumberGenerator billNumberGenerator;
    private final FolioValidationService validationService;

    // ---------------- CREATE FOLIO ----------------

    @Override
    @Transactional
    public FolioResponse createFolio(CreateFolioRequest request, String tenantId, String userId) {

        Folio folio = new Folio();
        folio.setTenantId(tenantId);
        folio.setCustomerId(request.getCustomerId());
        folio.setReferenceId(request.getReferenceId());
        folio.setStatus(FolioStatus.OPEN);
        folio.setCurrency(request.getCurrency());
        folio.setCreatedBy(userId);

        folio = folioRepository.save(folio);

        return folioMapper.toFolioResponse(folio);
    }

    // ---------------- ADD CHARGE ----------------

    @Override
    @Transactional
    public ChargeResponse addCharge(String folioId, AddChargeRequest request, String tenantId, String userId) {

        Folio folio = getValidFolio(folioId, tenantId);

        validationService.validateFolioNotLocked(folio);

        if (folioItemRepository.existsByTenantIdAndReferenceId(tenantId, request.getReferenceId())) {
            throw  new DuplicateChargeException(ExceptionMessageConstants.DUPLICATE_CHARGE);
        }

        FolioItem item = new FolioItem();
        item.setTenantId(tenantId);
        item.setFolioId(folioId);
        item.setType(FolioItemType.CHARGE);
        item.setAmount(request.getAmount());
        item.setSource(request.getSource());
        item.setReferenceId(request.getReferenceId());
        item.setDescription(request.getDescription());
        item.setQuantity(request.getQuantity());
        item.setTaxAmount(request.getTaxAmount());
        item.setCreatedBy(userId);

        folioItemRepository.save(item);

        updateFolioTotals(folio, request.getAmount(), BigDecimal.ZERO);

        return folioMapper.toChargeResponse(item, folio.getBalance());
    }

    // ---------------- ADD PAYMENT ----------------

    @Override
    @Transactional
    public PaymentResponse addPayment(String folioId, AddPaymentRequest request, String tenantId, String userId) {

        Folio folio = getValidFolio(folioId, tenantId);

        validationService.validateFolioNotLocked(folio);

        paymentRepository.findByTransactionId(request.getTransactionId()).ifPresent(pymnt -> {
            throw new DuplicateTransactionException(ExceptionMessageConstants.DUPLICATE_TRANSACTION);
        });

        String paymentRef = "PAY_" + UUID.randomUUID();

        FolioItem item = new FolioItem();
        item.setTenantId(tenantId);
        item.setFolioId(folioId);
        item.setType(FolioItemType.PAYMENT);
        item.setAmount(request.getAmount());
        item.setSource("PAYMENT");
        item.setReferenceId(paymentRef);
        item.setCreatedBy(userId);

        item = folioItemRepository.save(item);

        Payment payment = new Payment();
        payment.setTenantId(tenantId);
        payment.setFolioId(folioId);
        payment.setFolioItemId(item.getId());
        payment.setPaymentType(PaymentType.valueOf(request.getPaymentType()));
        payment.setMethod(PaymentMethod.valueOf(request.getMethod()));
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setAmount(request.getAmount());
        payment.setTransactionId(request.getTransactionId());
        payment.setCreatedBy(userId);

        payment = paymentRepository.save(payment);

        payment.setFolioItemId(item.getId());
        paymentRepository.save(payment);

        updateFolioTotals(folio, BigDecimal.ZERO, request.getAmount());

        return folioMapper.toPaymentResponse(payment, folio.getBalance());
    }

    // ---------------- GET FOLIO ----------------

    @Override
    public FolioDetailResponse getFolio(String folioId, String tenantId) {

        Folio folio = getValidFolio(folioId, tenantId);

        List<FolioItem> items =
                folioItemRepository.findByFolioIdAndTenantIdOrderByCreatedAtAsc(folioId, tenantId);

        return folioMapper.toFolioDetailResponse(folio, items);
    }


    //----------------------------CLOSE-A-FOLIO--------------------------
    @Override
    @Transactional
    public CloseFolioResponse closeFolio(String folioId, String tenantId, String userId) {

        Folio folio = getValidFolio(folioId, tenantId);

        // 1. Check already closed
        validationService.validateFolioOpen(folio);

        // 2. Check pending balance
        validationService.validateSufficientBalanceForClosure(folio);

        // 3. Generate bill number
        String billNumber = billNumberGenerator.generate();

        // 4. Update folio
        folio.setStatus(FolioStatus.CLOSED);
        folio.setLocked(true);
        folio.setBillNumber(billNumber);
        folio.setClosedAt(LocalDateTime.now());
        folio.setClosedBy(userId);

        folioRepository.save(folio);

        // 5. Response
        CloseFolioResponse response = new CloseFolioResponse();
        response.setFolioId(folio.getId());
        response.setBillNumber(billNumber);
        response.setStatus(folio.getStatus().name());
        response.setClosedAt(folio.getClosedAt());

        return response;
    }

    @Override
    @Transactional
    public ReverseChargeResponse reverseCharge(String folioId,
                                               ReverseChargeRequest request,
                                               String tenantId,
                                               String userId,
                                               String userRole) {

        // 1. Fetch & validate folio
        Folio folio = getValidFolio(folioId, tenantId);

        validationService.validateFolioNotLocked(folio);

        // 2. Fetch original entry
        FolioItem original = folioItemRepository
                .findByTenantIdAndReferenceId(tenantId, request.getReferenceId())
                .orElseThrow(() ->
                        new FolioNotFoundException("Original entry not found for reference: "
                                + request.getReferenceId()));

        // 3. Validate reversal rules (date + role)
        validationService.validateReversalAllowed(original, userRole);

        // 4. Prevent reversing non-charge entries (optional but recommended)
        if (original.getType() != FolioItemType.CHARGE) {
            throw new InvalidFolioStateException("Only charge entries can be reversed");
        }

        // 5. Prevent double reversal
        boolean alreadyReversed = folioItemRepository
                .existsByTenantIdAndParentItemId(tenantId, original.getId());

        if (alreadyReversed) {
            throw new InvalidFolioStateException("This entry is already reversed");
        }

        // 6. Create reversal entry
        FolioItem reversal = new FolioItem();
        reversal.setTenantId(tenantId);
        reversal.setFolioId(folioId);
        reversal.setType(FolioItemType.REVERSAL);
        reversal.setAmount(original.getAmount().negate());
        reversal.setSource(original.getSource());
        reversal.setReferenceId("REV_" + UUID.randomUUID());
        reversal.setDescription("Reversal of " + request.getReferenceId()
                + " | Reason: " + request.getReason());
        reversal.setParentItemId(original.getId());

        folioItemRepository.save(reversal);

        // 7. Update folio totals
        updateFolioTotals(folio, reversal.getAmount(), BigDecimal.ZERO);

        // 8. Prepare response
        ReverseChargeResponse response = new ReverseChargeResponse();
        response.setFolioItemId(reversal.getId());
        response.setReversedReferenceId(request.getReferenceId());
        response.setAmount(reversal.getAmount());
        response.setUpdatedBalance(folio.getBalance());

        return response;
    }

    // ---------------- HELPERS ----------------

    private Folio getValidFolio(String folioId, String tenantId) {
        Folio folio = folioRepository.findByIdAndTenantId(folioId, tenantId).orElseThrow(() -> new FolioNotFoundException("Folio not found: " + folioId));
        return folio;
    }

    private void updateFolioTotals(Folio folio, BigDecimal charge, BigDecimal payment) {
        folio.setTotalCharges(folio.getTotalCharges().add(charge));
        folio.setTotalPayments(folio.getTotalPayments().add(payment));
        folio.setBalance(folio.getTotalCharges().subtract(folio.getTotalPayments()));
        folioRepository.save(folio);
    }
}