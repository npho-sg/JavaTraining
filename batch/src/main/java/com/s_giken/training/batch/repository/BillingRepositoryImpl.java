package com.s_giken.training.batch.repository;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BillingRepositoryImpl implements BillingRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public boolean isConfirmed(String ym) {
		String sql = "SELECT COUNT(*) FROM T_BILLING_STATUS WHERE billing_ym = ? AND is_commited = TRUE ";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, ym);
		return count == 1 ? true : false;

	}

	@Transactional
	@Override
	public void deleteByMonth(String ym) {

		jdbcTemplate.update("DELETE FROM T_BILLING_DETAIL_DATA WHERE billing_ym = ? ", ym);
		jdbcTemplate.update("DELETE FROM T_BILLING_DATA WHERE billing_ym = ? ", ym);
		jdbcTemplate.update("DELETE FROM T_BILLING_STATUS WHERE billing_ym = ? ", ym);

	}

	@Transactional
	@Override
	public void insertStatus(String ym) {
		jdbcTemplate.update("INSERT INTO T_BILLING_STATUS (billing_ym, is?commited) VALUES (?, FALSE) ", ym);
	}
	
	
}
