package com.atria.userservice.service;

import com.atria.userservice.dto.UserRequestDTO;
import com.atria.userservice.dto.UserResponseDTO;
import com.atria.userservice.dto.UserResponseObject;

import java.util.List;

public interface IUserService {

    UserResponseObject createUser(UserRequestDTO dto);

    UserResponseObject getUserById(Long id);

    List<UserResponseObject> getAllUsers();

    UserResponseObject updateUser(Long id, UserRequestDTO dto);

    Boolean deleteUser(Long id);
}