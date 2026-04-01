package com.atria.billingservice.repository;

import com.atria.billingservice.entity.Folio;
import com.atria.billingservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findByTransactionId(String transactionId);

    Optional<Payment> findByFolioItemId(String id);
}