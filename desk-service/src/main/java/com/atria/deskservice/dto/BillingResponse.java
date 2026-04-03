package com.atria.deskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingResponse<T> {

    private String status;
    private String message;
    private T data;
}