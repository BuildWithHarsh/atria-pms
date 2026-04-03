package com.atria.deskservice.controller;

import com.atria.deskservice.dto.BillingResponse;
import com.atria.deskservice.dto.CreateRoomRequest;
import com.atria.deskservice.dto.RoomResponse;
import com.atria.deskservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // 🔹 Create Room (Admin)
    @PostMapping
    public ResponseEntity<BillingResponse<String>> createRoom(
            @RequestBody CreateRoomRequest request,
            @RequestHeader("X-Tenant-Id") String tenantId
    ) {

        String roomId = roomService.createRoom(request, tenantId);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Room created successfully", roomId)
        );
    }

    // 🔹 Get All Rooms
    @GetMapping
    public ResponseEntity<BillingResponse<List<RoomResponse>>> getAllRooms(
            @RequestHeader("X-Tenant-Id") String tenantId
    ) {

        List<RoomResponse> rooms = roomService.getAllRooms(tenantId);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Rooms fetched", rooms)
        );
    }

    // 🔹 Get Available Rooms
    @GetMapping("/available")
    public ResponseEntity<BillingResponse<List<RoomResponse>>> getAvailableRooms(
            @RequestHeader("X-Tenant-Id") String tenantId
    ) {

        List<RoomResponse> rooms = roomService.getAvailableRooms(tenantId);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Available rooms", rooms)
        );
    }

    // 🔹 Update Room Status
    @PatchMapping("/{roomId}/status")
    public ResponseEntity<BillingResponse<String>> updateRoomStatus(
            @PathVariable String roomId,
            @RequestParam String status,
            @RequestHeader("X-Tenant-Id") String tenantId
    ) {

        roomService.updateRoomStatus(roomId, status, tenantId);

        return ResponseEntity.ok(
                new BillingResponse<>("SUCCESS", "Room status updated", "OK")
        );
    }
}