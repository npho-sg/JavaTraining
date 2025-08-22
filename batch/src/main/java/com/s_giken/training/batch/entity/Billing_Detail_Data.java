package com.s_giken.training.batch.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Billing_Detail_Data {
	
	  private String billingMonth;
	    private String subscriberId;
	    private String feeId;
	    private String feeName;
	    private int monthlyFee;
	    private LocalDate startDate;
	    private LocalDate endDate;

}
