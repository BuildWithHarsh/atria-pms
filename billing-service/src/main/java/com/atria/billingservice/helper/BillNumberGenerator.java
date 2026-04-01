package com.atria.billingservice.helper;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class BillNumberGenerator {

    public String generate() {
        return "BILL-" + LocalDate.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 6);
    }
}