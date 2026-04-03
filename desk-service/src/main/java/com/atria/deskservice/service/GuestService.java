package com.atria.deskservice.service;

import com.atria.deskservice.dto.CreateGuestRequest;
import com.atria.deskservice.dto.GuestResponse;

public interface GuestService {

    GuestResponse createGuest(CreateGuestRequest request, String tenantId);
}