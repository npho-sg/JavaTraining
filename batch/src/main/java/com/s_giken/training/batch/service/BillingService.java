package com.s_giken.training.batch.service;

import java.util.Optional;

public interface BillingService {
	
	public Optional<Integer> isConfirmed(String ym);

	public boolean resetStatus(String ym, String ymf);
	
	public boolean resetDataAndDetail(String ym, String ymf);

}
