package com.atria.billingservice.repository;

import com.atria.billingservice.entity.FolioItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolioItemRepository extends JpaRepository<FolioItem, String> {

    boolean existsByTenantIdAndReferenceId(String tenantId, String referenceId);

    List<FolioItem> findByFolioIdAndTenantIdOrderByCreatedAtAsc(String folioId, String tenantId);

    Optional<FolioItem> findByTenantIdAndReferenceId(String tenantId, String referenceId);

    boolean existsByTenantIdAndParentItemId(String tenantId, String id);
}