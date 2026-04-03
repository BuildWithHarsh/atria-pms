package com.atria.deskservice.repository;

import com.atria.deskservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    List<Room> findByTenantIdAndStatus(String tenantId, String status);

    Optional<Room> findByIdAndTenantId(String id, String tenantId);

    @Query("""
            SELECT COUNT(r)
            FROM Room r
            WHERE r.roomTypeId = :roomTypeId
            """)
    int getTotalRooms(String roomTypeId);
}