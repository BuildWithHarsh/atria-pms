package com.atria.userservice.controllers;

import com.atria.userservice.constants.UserServiceConstants;
import com.atria.userservice.dto.*;
import com.atria.userservice.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto<UserResponseDto>> register(
            @Valid @RequestBody UserRequestDto request) {

        UserResponseDto userResponseDto = authService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponseDto<UserResponseDto>(UserServiceConstants.STATUS_200, UserServiceConstants.MESSAGE_200, "", "", "", userResponseDto));
    }


    /*@PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }*/
}