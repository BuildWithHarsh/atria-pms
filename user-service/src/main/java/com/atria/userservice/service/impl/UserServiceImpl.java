package com.atria.userservice.service.impl;

import com.atria.userservice.dto.UserRequestDTO;
import com.atria.userservice.dto.UserResponseDTO;
import com.atria.userservice.dto.UserResponseObject;
import com.atria.userservice.entity.Role;
import com.atria.userservice.entity.User;
import com.atria.userservice.exception.UserAlreadyExistsException;
import com.atria.userservice.exception.UserNotFoundException;
import com.atria.userservice.mapper.UserMapper;
import com.atria.userservice.repositories.RoleRepository;
import com.atria.userservice.repositories.UserRepository;
import com.atria.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseObject createUser(UserRequestDTO dto) {

        // ✅ Check duplicate email/username
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with the given email address");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("User already exists with the given username");
        }


        User user = UserMapper.toEntity(dto);


        user.setPassword(passwordEncoder.encode(dto.getPassword()));


        Set<Role> roles = roleRepository.findByNameIn(dto.getRoles());

        if (roles.isEmpty()) {
            throw new RuntimeException("Invalid roles provided");
        }

        user.setRoles(roles);

        // ✅ Save
        User savedUser = userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserResponseObject getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User","ID",String.valueOf(id)));

        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserResponseObject> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseObject updateUser(Long id, UserRequestDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User","ID",String.valueOf(id)));

        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setImage(dto.getImage());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<Role> roles = roleRepository.findByNameIn(dto.getRoles());

            if (roles.isEmpty()) {
                throw new RuntimeException("Invalid roles provided");
            }

            user.setRoles(roles);
        }

        User updatedUser = userRepository.save(user);

        return UserMapper.toDTO(updatedUser);
    }

    @Override
    public Boolean  deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User","ID",String.valueOf(id));
        }
       userRepository.deleteById(id);
        return true;
    }
}