package com.s_giken.training.webapp.repository.jdbc;

import java.sql.Types;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.s_giken.training.webapp.model.entity.Charge;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChargeRepositoryImpl implements ChargeRepository {

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Charge> rowMapper;

	@Override
	public Optional<Charge> findByChargeId(Long chargeId) {

		String sql = "SELECT * FROM T_CHARGE WHERE charge_id = ?";
		Object[] args = { chargeId };
		int[] argTypes = { Types.BIGINT };

		try {
			Charge charge = jdbcTemplate.queryForObject(sql, args, argTypes, rowMapper);
			return Optional.of(charge);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}

	}

	@Override
	public int add(Charge charge) {
		Long chargeId = charge.getChargeId();
		if (chargeId == null) {
			chargeId = jdbcTemplate.queryForObject("SELECT NEXT VALUE FOR t_charge_seq", Long.class);
			charge.setChargeId(chargeId);
		}

		String sql = """
				        INSERT INTO T_CHARGE (charge_id, name, amount, start_date, end_date, created_at, modified_at)
				        VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
				""";
		int processed_count = jdbcTemplate.update(

				sql,
				chargeId,
				charge.getChargeName(),
				charge.getAmount(),
				charge.getStartDate(),
				charge.getEndDate());

		return processed_count;
	}

	@Override
	public int update(Charge charge) {
		String sql = """
				    UPDATE T_CHARGE
				    SET
				        name = ?,
				        amount = ?,
				        start_date = ?,
				        end_date = ?,
				        modified_at = CURRENT_TIMESTAMP
				    WHERE charge_id = ?
				""";
		int processed_count = jdbcTemplate.update(
				sql,
				charge.getChargeName(),
				charge.getAmount(),
				charge.getStartDate(),
				charge.getEndDate(),
				charge.getChargeId());

		return processed_count;
	}

	@Override
	public int deleteByChargeId(Long chargeId) {
		String sql = "DELETE FROM T_CHARGE WHERE charge_id = ?";

		int processed_count = jdbcTemplate.update(sql, chargeId);

		return processed_count;
	}

}
