package com.atria.billingservice.repository;

import com.atria.billingservice.entity.Folio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FolioRepository extends JpaRepository<Folio, String> {

    Optional<Folio> findByIdAndTenantId(String id, String tenantId);
}