package com.atria.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ExpiredJwtCustomException extends RuntimeException {

    public ExpiredJwtCustomException(String message) {
        super(message);
    }
}