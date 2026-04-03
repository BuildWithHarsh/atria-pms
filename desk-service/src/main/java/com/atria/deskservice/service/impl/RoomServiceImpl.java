package com.atria.deskservice.service.impl;

import com.atria.deskservice.dto.CreateRoomRequest;
import com.atria.deskservice.dto.RoomResponse;
import com.atria.deskservice.entity.Room;
import com.atria.deskservice.mapper.RoomMapper;
import com.atria.deskservice.repository.RoomRepository;
import com.atria.deskservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public String createRoom(CreateRoomRequest request, String tenantId) {

        Room room = roomMapper.toEntity(request, tenantId);

        roomRepository.save(room);

        return room.getId();
    }

    @Override
    public List<RoomResponse> getAllRooms(String tenantId) {

        return roomRepository.findAll()
                .stream()
                .filter(r -> r.getTenantId().equals(tenantId))
                .map(roomMapper::toDto)
                .toList();
    }

    @Override
    public List<RoomResponse> getAvailableRooms(String tenantId) {

        return roomRepository.findByTenantIdAndStatus(tenantId, "AVAILABLE")
                .stream()
                .map(roomMapper::toDto)
                .toList();
    }

    @Override
    public void updateRoomStatus(String roomId, String status, String tenantId) {

        Room room = roomRepository.findByIdAndTenantId(roomId, tenantId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setStatus(status);

        roomRepository.save(room);
    }
}