package com.atria.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class ErrorResponseDto {

    private  String apiPath;

    private String errorCode;

    private  String errorMessage;

    private String errorTime;

    public ErrorResponseDto(String apiPath, String errorCode, Exception exception) {
        this.apiPath = apiPath;
        this.errorCode = errorCode;
        this.errorMessage=exception.getMessage();
        this.errorTime= LocalDateTime.now().toString();
    }



}
