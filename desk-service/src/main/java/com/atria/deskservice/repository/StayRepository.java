package com.atria.deskservice.repository;

import com.atria.deskservice.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StayRepository extends JpaRepository<Stay, String> {

    Optional<Stay> findByRoomIdAndStatus(String roomId, String status);

    Optional<Stay> findByIdAndTenantId(String id, String tenantId);
}