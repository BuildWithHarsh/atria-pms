package com.atria.deskservice.repository;

import com.atria.deskservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

    /*@Query("""
                SELECT COUNT(r) FROM Reservation r
                WHERE r.roomTypeId = :roomTypeId
                AND r.status = 'BOOKED'
                AND r.checkInDate < :checkOutDate
                AND r.checkOutDate > :checkInDate
            """)
    int countOverlappingReservations(String roomTypeId,
                                     LocalDate checkInDate,
                                     LocalDate checkOutDate);*/

    @Query("""
            SELECT COALESCE(SUM(r.roomCount), 0)
            FROM Reservation r
            WHERE r.roomTypeId = :roomTypeId
            AND r.status = 'BOOKED'
            AND r.checkInDate < :checkOutDate
            AND r.checkOutDate > :checkInDate
            """)
    int getReservedRoomCount(String roomTypeId,
                             LocalDate checkInDate,
                             LocalDate checkOutDate);
}