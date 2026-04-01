package com.atria.billingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "folio_items",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_reference", columnNames = {"tenant_id", "reference_id"})
       },
       indexes = {
           @Index(name = "idx_folio_item_folio", columnList = "folio_id"),
           @Index(name = "idx_folio_item_tenant", columnList = "tenant_id"),
           @Index(name = "idx_folio_item_type", columnList = "type")
       })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolioItem extends  BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "folio_id", nullable = false)
    private String folioId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FolioItemType type;
    // CHARGE, PAYMENT, REFUND, ADJUSTMENT

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency = "INR";

    // Source of charge
    @Column(nullable = false)
    private String source; 
    // FNB, LAUNDRY, ROOM, SPA, MANUAL

    // External reference (VERY IMPORTANT)
    @Column(name = "reference_id", nullable = false)
    private String referenceId;

    // Description for invoice
    @Column(name = "description")
    private String description;

    // Quantity support (for future)
    private Integer quantity = 1;

    // Tax fields (future ready)
    @Column(name = "tax_amount")
    private BigDecimal taxAmount;

    @Column(name = "net_amount")
    private BigDecimal netAmount;

    @Column(name = "gross_amount")
    private BigDecimal grossAmount;

    // Reversal support
    @Column(name = "parent_item_id")
    private String parentItemId;

    // Metadata (flexible JSON)
    @Column(columnDefinition = "TEXT")
    private String metadata;


}