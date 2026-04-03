package com.atria.deskservice.service.impl;

import com.atria.deskservice.dto.CreateGuestRequest;
import com.atria.deskservice.dto.GuestResponse;
import com.atria.deskservice.entity.Guest;
import com.atria.deskservice.mapper.GuestMapper;
import com.atria.deskservice.repository.GuestRepository;
import com.atria.deskservice.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    @Override
    public GuestResponse createGuest(CreateGuestRequest request, String tenantId) {

        Guest guest = guestMapper.toEntity(request, tenantId);

        guestRepository.save(guest);



        return guestMapper.toDto(guest);
    }
}