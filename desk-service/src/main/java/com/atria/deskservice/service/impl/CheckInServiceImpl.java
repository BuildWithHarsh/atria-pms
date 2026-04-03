package com.atria.deskservice.service.impl;

import com.atria.deskservice.client.BillingClient;
import com.atria.deskservice.dto.*;
import com.atria.deskservice.entity.Room;
import com.atria.deskservice.entity.Stay;
import com.atria.deskservice.mapper.StayMapper;
import com.atria.deskservice.repository.RoomRepository;
import com.atria.deskservice.repository.StayRepository;
import com.atria.deskservice.service.CheckInService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckInServiceImpl implements CheckInService {

    private final RoomRepository roomRepository;
    private final StayRepository stayRepository;
    private final StayMapper stayMapper;
    private final BillingClient billingClient;

    @Override
    @Transactional
    public CheckInResponse checkIn(CheckInRequest request, String tenantId) {

        // 1. Validate room
        Room room = roomRepository.findByIdAndTenantId(
                request.getRoomId(), tenantId
        ).orElseThrow(() -> new RuntimeException("Room not found"));

        if (!"AVAILABLE".equals(room.getStatus())) {
            throw new RuntimeException("Room not available");
        }

        // 2. Create folio (billing)
        CreateFolioRequest folioRequest = new CreateFolioRequest();
        folioRequest.setCustomerId(request.getGuestId());
        folioRequest.setReferenceId("STAY_" + UUID.randomUUID());

        BillingResponse<FolioResponse> response =
                billingClient.createFolio(tenantId, folioRequest);

        String folioId = response.getData().getFolioId();

        // 3. Create stay
        Stay stay = stayMapper.toEntity(request, tenantId, folioId);

        stayRepository.save(stay);

        // 4. Update room
        room.setStatus("OCCUPIED");
        roomRepository.save(room);

        CheckInResponse checkInResponse = new CheckInResponse();
        checkInResponse.setStayId(stay.getId());
        checkInResponse.setFolioId(folioId);
        checkInResponse.setGuestId(stay.getGuestId());
        checkInResponse.setRoomId(room.getId());
        checkInResponse.setRoomNumber(room.getRoomNumber());
        checkInResponse.setCheckInTime(stay.getCheckInTime());
        checkInResponse.setStatus(stay.getStatus());

        return checkInResponse;
    }
}