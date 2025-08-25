package com.s_giken.training.batch.repository;

import java.util.Optional;

public interface BillingRepository {
	
    Optional<Integer> isConfirmed(String ym);
    void deleteByMonth(String ym);
    void updateStatus(String ym);
    int updateData(String ym);
    int updateDetail(String ym);
}
