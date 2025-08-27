package com.s_giken.training.batch.repository;

import java.util.Optional;

public interface BillingRepository {

	Optional<Integer> getConfirmedCount(String ym);

	public void deleteByMonth(String ym);

	public void insertStatus(String ym);

	public int insertData(String ym);

	public int updateDataToAmount();

	public int insertDetail(String ym);
}
