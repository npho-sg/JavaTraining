package com.s_giken.training.batch.repository;

import java.util.Optional;

public interface BillingRepository {
	
    Optional<Integer> getConfirmedCount(String ym);
    void deleteByMonth(String ym);
    void insertStatus(String ym);
    int insertData(String ym);
    public int updateDataToAmount();
    int insertDetail(String ym);
}
