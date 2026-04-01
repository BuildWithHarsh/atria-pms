package com.atria.billingservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingResponse<T> {

    private String status;   // SUCCESS / ERROR
    private String message;
    private T data;
}