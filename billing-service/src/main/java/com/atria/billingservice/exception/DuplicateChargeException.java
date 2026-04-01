package com.atria.billingservice.exception;

public class DuplicateChargeException extends RuntimeException {

    public DuplicateChargeException(String message) {
        super(message);
    }
}