package com.atria.userservice.controllers;

import com.atria.userservice.constants.UserServiceConstants;
import com.atria.userservice.dto.ResponseDto;
import com.atria.userservice.dto.UserRequestDTO;
import com.atria.userservice.dto.UserResponseDTO;
import com.atria.userservice.dto.UserResponseObject;
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
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO requestDTO) {
        UserResponseObject response = IUserService.createUser(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserResponseDTO(UserServiceConstants.STATUS_201, UserServiceConstants.MESSAGE_201, response));
    }

    /*--------------------GET A USER-----------------------*/
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseObject> getUserById(@PathVariable Long id) {
        UserResponseObject response = IUserService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*--------------------GET ALL USER-----------------------*/
    @GetMapping
    public ResponseEntity<List<UserResponseObject>> getAllUsers() {
        List<UserResponseObject> users = IUserService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    /*--------------------UPDATE A USER-----------------------*/
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO requestDTO) {
        try{
            UserResponseObject updatedUser = IUserService.updateUser(id, requestDTO);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new UserResponseDTO(UserServiceConstants.STATUS_200, UserServiceConstants.MESSAGE_200, updatedUser));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new UserResponseDTO("417", e.getMessage(),null));
        }
    }

    /*--------------------DELETE A USER-----------------------*/
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable Long id) {
        Boolean isDeleted = IUserService.deleteUser(id);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(UserServiceConstants.STATUS_200, UserServiceConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(UserServiceConstants.STATUS_417, UserServiceConstants.MESSAGE_417_DELETE));
        }
    }
}