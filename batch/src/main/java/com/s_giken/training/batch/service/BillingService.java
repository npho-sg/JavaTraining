package com.s_giken.training.batch.service;

public interface BillingService {
	
	public boolean isConfirmed(String ym);

	public boolean resetStatus(String ym);

}
