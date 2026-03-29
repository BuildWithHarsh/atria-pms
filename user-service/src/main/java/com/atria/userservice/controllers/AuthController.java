package com.atria.userservice.controllers;

import com.atria.userservice.constants.UserServiceConstants;
import com.atria.userservice.dto.*;
import com.atria.userservice.entity.RefreshToken;
import com.atria.userservice.entity.User;
import com.atria.userservice.mapper.UserMapper;
import com.atria.userservice.repositories.RefreshTokenRepository;
import com.atria.userservice.repositories.UserRepository;
import com.atria.userservice.security.JwtService;
import com.atria.userservice.service.IAuthService;
import com.atria.userservice.util.RandomGeneraterUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto<UserResponseDto>> register(
            @Valid @RequestBody UserRequestDto request) {

        UserResponseDto userResponseDto = authService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponseDto<UserResponseDto>(UserServiceConstants.STATUS_200, UserServiceConstants.MESSAGE_200, "", "", "", userResponseDto));
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @Valid @RequestBody LoginRequest request) {

        Authentication authenticated = authenticate(request);
        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> new BadCredentialsException("Username or Password is invalid"));

        if(!user.isEnabled()){
            throw new DisabledException("User is Disabled");
        }

        long refreshTokenId = RandomGeneraterUtil.generateTenDigitNumber();
        RefreshToken refreshTokenObj = RefreshToken.builder().jti(String.valueOf(refreshTokenId)).user(user).createAt(String.valueOf(new Date(System.currentTimeMillis()))).expiresAt(String.valueOf(new Date(System.currentTimeMillis() + jwtService.getRefreshTokenExpiration()))).build();

        refreshTokenRepository.save(refreshTokenObj);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user, refreshTokenObj.getJti());
        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken, jwtService.getAccessTokenExpiration(), "Bearer", UserMapper.toDTO(user));
        return ResponseEntity.ok(tokenResponse);
    }

    private Authentication authenticate(@Valid LoginRequest request) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        } catch (Exception e) {
            throw new BadCredentialsException("Username or Password is invalid");
        }

    }
}