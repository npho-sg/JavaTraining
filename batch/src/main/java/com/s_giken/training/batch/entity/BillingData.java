package com.s_giken.training.batch.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingData {

    private String billingMonth;
    private String subscriberId;
    private String email;
    private String name;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private String paymentMethod;
    private int billingAmount;
    private double taxRate;
    private int totalAmount;

}