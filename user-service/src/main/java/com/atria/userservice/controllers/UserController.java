package com.atria.userservice.controllers;

import com.atria.userservice.constants.UserServiceConstants;
import com.atria.userservice.dto.ApiResponseDto;
import com.atria.userservice.dto.UserRequestDto;
import com.atria.userservice.dto.UserResponseDto;
import com.atria.userservice.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService IUserService;

    /*--------------------CREATE A USER-----------------------*/
    @PostMapping
    public ResponseEntity<ApiResponseDto<UserResponseDto>> createUser(
            @Valid @RequestBody UserRequestDto requestDTO) {
        UserResponseDto response = IUserService.createUser(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponseDto<UserResponseDto>(UserServiceConstants.STATUS_201, UserServiceConstants.MESSAGE_201, response));
    }

    /*--------------------GET A USER-----------------------*/
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto response = IUserService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*--------------------GET ALL USER-----------------------*/
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = IUserService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    /*--------------------UPDATE A USER-----------------------*/
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto requestDTO) {
        try{
            UserResponseDto updatedUser = IUserService.updateUser(id, requestDTO);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<UserResponseDto>(UserServiceConstants.STATUS_200, UserServiceConstants.MESSAGE_200, updatedUser));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ApiResponseDto<UserResponseDto>("417", e.getMessage(),null));
        }
    }

    /*--------------------DELETE A USER-----------------------*/
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> deleteUser(@PathVariable Long id) {
        Boolean isDeleted = IUserService.deleteUser(id);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto<UserResponseDto>(UserServiceConstants.STATUS_200, UserServiceConstants.MESSAGE_200,null));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ApiResponseDto<UserResponseDto>(UserServiceConstants.STATUS_417, UserServiceConstants.MESSAGE_417_DELETE, null));
        }
    }
}