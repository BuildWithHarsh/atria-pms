package com.atria.deskservice.service;

import com.atria.deskservice.dto.CheckInRequest;
import com.atria.deskservice.dto.CheckInResponse;

public interface CheckInService {

    CheckInResponse checkIn(CheckInRequest request, String tenantId);
}