package com.atria.billingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "folios",
       indexes = {
           @Index(name = "idx_folio_tenant", columnList = "tenant_id"),
           @Index(name = "idx_folio_customer", columnList = "customer_id"),
           @Index(name = "idx_folio_status", columnList = "status")
       })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Folio extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    // Optional: link with PMS booking
    @Column(name = "reference_id")
    private String referenceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FolioStatus status; // OPEN, CLOSED, CANCELLED

    @Column(name = "currency", nullable = false)
    private String currency = "INR";

    // Optional optimization (derived but cached)
    @Column(name = "total_charges")
    private BigDecimal totalCharges = BigDecimal.ZERO;

    @Column(name = "total_payments")
    private BigDecimal totalPayments = BigDecimal.ZERO;

    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;

    // Business flags
    @Column(name = "is_locked")
    private boolean locked = false; // after checkout

    @Version
    private Long version; // optimistic locking

    @Column(name = "bill_number")
    private String billNumber;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "closed_by")
    private String closedBy;
}