package com.atria.userservice.service;

import com.atria.userservice.dto.UserRequestDto;
import com.atria.userservice.dto.UserResponseDto;

import java.util.List;

public interface IUserService {

    UserResponseDto createUser(UserRequestDto dto);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    UserResponseDto updateUser(Long id, UserRequestDto dto);

    Boolean deleteUser(Long id);
}