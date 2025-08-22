package com.s_giken.training.batch.repository;

public interface BillingRepository {
	
    boolean isConfirmed(String ym);
    void deleteByMonth(String ym);
    void insertStatus(String ym);
}
