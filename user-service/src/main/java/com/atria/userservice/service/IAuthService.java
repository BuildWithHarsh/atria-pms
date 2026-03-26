package com.atria.userservice.service;

import com.atria.userservice.dto.UserRequestDTO;
import com.atria.userservice.dto.UserResponseDTO;
import com.atria.userservice.dto.UserResponseObject;

public interface IAuthService {
    UserResponseObject registerUser(UserRequestDTO userRequestDTO);
}
