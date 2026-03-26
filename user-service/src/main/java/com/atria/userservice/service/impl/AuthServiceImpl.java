package com.atria.userservice.service.impl;

import com.atria.userservice.dto.UserRequestDTO;
import com.atria.userservice.dto.UserResponseDTO;
import com.atria.userservice.dto.UserResponseObject;
import com.atria.userservice.service.IAuthService;
import com.atria.userservice.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IUserService userService;
    @Override
    public UserResponseObject registerUser(UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);

    }
}
