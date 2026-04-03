package com.atria.deskservice.mapper;

import com.atria.deskservice.dto.CreateRoomRequest;
import com.atria.deskservice.dto.RoomResponse;
import com.atria.deskservice.entity.Room;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoomMapper {

    public Room toEntity(CreateRoomRequest req, String tenantId) {

        Room room = new Room();

        room.setId(UUID.randomUUID().toString());
        room.setTenantId(tenantId);
        room.setRoomNumber(req.getRoomNumber());
        room.setRoomTypeId(req.getRoomTypeId());
        room.setFloor(req.getFloor());
        room.setCapacity(req.getCapacity());

        room.setStatus("AVAILABLE"); // default

        return room;
    }

    public RoomResponse toDto(Room room) {

        RoomResponse dto = new RoomResponse();

        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomTypeId(room.getRoomTypeId());
        dto.setStatus(room.getStatus());
        dto.setFloor(room.getFloor());
        dto.setCapacity(room.getCapacity());

        return dto;
    }
}