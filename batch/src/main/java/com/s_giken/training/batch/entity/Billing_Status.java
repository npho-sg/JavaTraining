package com.s_giken.training.batch.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Billing_Status {
	
	private String billingYm;
	private boolean commited;
	private Timestamp timeStamp;
	private Timestamp modifiedAt;
	
}