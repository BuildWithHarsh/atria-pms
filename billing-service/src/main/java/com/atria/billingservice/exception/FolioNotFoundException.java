package com.atria.billingservice.exception;

public class FolioNotFoundException extends RuntimeException {

    public FolioNotFoundException(String message) {
        super(message);
    }
}