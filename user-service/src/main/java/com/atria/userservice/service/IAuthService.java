package com.atria.userservice.service;

import com.atria.userservice.dto.UserRequestDto;
import com.atria.userservice.dto.UserResponseDto;

public interface IAuthService {
    UserResponseDto registerUser(UserRequestDto userRequestDTO);
}
