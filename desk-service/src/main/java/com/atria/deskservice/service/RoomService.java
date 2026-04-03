package com.atria.deskservice.service;

import com.atria.deskservice.dto.CreateRoomRequest;
import com.atria.deskservice.dto.RoomResponse;

import java.util.List;

public interface RoomService {

    String createRoom(CreateRoomRequest request, String tenantId);

    List<RoomResponse> getAllRooms(String tenantId);

    List<RoomResponse> getAvailableRooms(String tenantId);

    void updateRoomStatus(String roomId, String status, String tenantId);
}