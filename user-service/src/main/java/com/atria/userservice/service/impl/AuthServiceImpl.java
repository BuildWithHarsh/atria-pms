package com.atria.userservice.service.impl;

import com.atria.userservice.dto.UserRequestDto;
import com.atria.userservice.dto.UserResponseDto;
import com.atria.userservice.service.IAuthService;
import com.atria.userservice.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IUserService userService;
    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDTO) {
        return userService.createUser(userRequestDTO);

    }
}
