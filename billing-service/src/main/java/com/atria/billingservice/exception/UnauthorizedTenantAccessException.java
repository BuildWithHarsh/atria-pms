package com.atria.billingservice.exception;

public class UnauthorizedTenantAccessException extends RuntimeException {

    public UnauthorizedTenantAccessException(String message) {
        super(message);
    }
}