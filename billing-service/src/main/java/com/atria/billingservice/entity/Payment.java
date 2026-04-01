package com.atria.billingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments",
       indexes = {
           @Index(name = "idx_payment_folio", columnList = "folio_id"),
           @Index(name = "idx_payment_tenant", columnList = "tenant_id"),
           @Index(name = "idx_payment_status", columnList = "status")
       })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment  extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "folio_id", nullable = false)
    private String folioId;

    // Link to folio item
    @Column(name = "folio_item_id", nullable = false)
    private String folioItemId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;
    // CASH, CARD, UPI, BANK_TRANSFER

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;
    // INITIATED, SUCCESS, FAILED, REFUNDED

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    private PaymentType paymentType;

    // Gateway details
    @Column(name = "gateway_reference")
    private String gatewayReference;

    @Column(name = "transaction_id")
    private String transactionId;

    // For reconciliation
    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    // Optional remarks
    private String remarks;

}