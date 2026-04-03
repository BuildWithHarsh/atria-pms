package com.atria.deskservice.repository;

import com.atria.deskservice.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, String> {

    Optional<Guest> findByIdAndTenantId(String id, String tenantId);
}