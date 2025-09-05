package com.s_giken.training.batch.repository;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BillingRepositoryImpl implements BillingRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public Optional<Integer> getConfirmedCount(String ym) {
		String sql = "SELECT COUNT(*) FROM T_BILLING_STATUS "
				+ "WHERE billing_ym = PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd') AND is_commit = TRUE ";
		try {
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, ym);
			return Optional.ofNullable(count);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public void deleteByMonth(String ym) {

		jdbcTemplate.update("DELETE FROM T_BILLING_DETAIL_DATA "
				+ "WHERE billing_ym = PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd') ", ym);
		jdbcTemplate.update("DELETE FROM T_BILLING_DATA "
				+ "WHERE billing_ym = PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd') ", ym);
		jdbcTemplate.update("DELETE FROM T_BILLING_STATUS "
				+ "WHERE billing_ym = PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd') ", ym);
	}

	@Override
	public void insertStatus(String ym) {
		jdbcTemplate.update(
				"INSERT INTO T_BILLING_STATUS (billing_ym, is_commit) "
						+ "VALUES (PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd'), FALSE) ",
				ym);
	}

	@Override
	public int insertData(String ym) {

		StringBuilder sb = new StringBuilder();

		sb.append("INSERT INTO T_BILLING_DATA (");
		sb.append(
				" billing_ym, member_id, mail, name, address, start_date, end_date, payment_method, amount, tax_ratio, total, modified_at ");
		sb.append(") ");
		sb.append("SELECT ");
		sb.append(" PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd'), ");
		sb.append(" m.member_id, ");
		sb.append(" m.mail, ");
		sb.append(" m.name, ");
		sb.append(" m.address, ");
		sb.append(" m.start_date, ");
		sb.append(" m.end_date, ");
		sb.append(" m.payment_method, ");
		sb.append(" (SELECT SUM(amount) FROM T_CHARGE ");
		sb.append("   WHERE start_date <= LAST_DAY(PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd')) ");
		sb.append("     AND (end_date IS NULL OR end_date >= PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd'))), ");
		sb.append(" 0.1, ");
		sb.append(" (SELECT SUM(amount) * (1 + 0.1) FROM T_CHARGE ");
		sb.append("   WHERE start_date <= LAST_DAY(PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd')) ");
		sb.append("     AND (end_date IS NULL OR end_date >= PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd'))), ");
		sb.append(" CURRENT_TIMESTAMP ");
		sb.append("FROM T_MEMBER m ");
		sb.append("WHERE m.start_date <= LAST_DAY(PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd')) ");
		sb.append("AND (m.end_date IS NULL OR m.end_date >= PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd'));");

		String sql = sb.toString();
		int result = jdbcTemplate.update(sql, ym, ym, ym, ym, ym, ym, ym);
		return result;
	}

	@Override
	public int insertDetail(String ym) {

		StringBuilder sb = new StringBuilder();

		sb.append("INSERT INTO T_BILLING_DETAIL_DATA (");
		sb.append(" billing_ym, member_id, charge_id, name, amount, start_date, end_date, modified_at ");
		sb.append(") ");
		sb.append("SELECT ");
		sb.append(" PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd'), ");
		sb.append(" a.member_id, ");
		sb.append(" b.charge_id, ");
		sb.append(" b.name, ");
		sb.append(" b.amount, ");
		sb.append(" b.start_date, ");
		sb.append(" b.end_date, ");
		sb.append(" CURRENT_TIMESTAMP ");
		sb.append("FROM T_MEMBER a ");
		sb.append("CROSS JOIN T_CHARGE b ");
		sb.append("WHERE ");
		sb.append("(");
		sb.append(" a.start_date <= LAST_DAY(PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd')) AND ");
		sb.append(" (a.end_date IS NULL OR a.end_date >= PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd')) ");
		sb.append(") ");
		sb.append("AND ");
		sb.append("(");
		sb.append(" b.start_date <= LAST_DAY(PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd')) AND ");
		sb.append(" (b.end_date IS NULL OR b.end_date >= PARSEDATETIME(CONCAT(? , '01'), 'yyyyMMdd')) ");
		sb.append(");");
		String sql = sb.toString();
		int result = jdbcTemplate.update(sql, ym, ym, ym, ym, ym);
		return result;
	}

}
