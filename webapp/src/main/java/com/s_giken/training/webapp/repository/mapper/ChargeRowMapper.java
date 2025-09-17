package com.s_giken.training.webapp.repository.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.s_giken.training.webapp.model.entity.Charge;

import lombok.NonNull;

@Component
public class ChargeRowMapper implements RowMapper<Charge> {
	/**
	 * マッピング処理を行うメソッド
	 * 
	 * @param rs     データベースからのレコードセット
	 * @param rowNum 処理行数
	 * 
	 * @return Chargeオブジェクト
	 */

	@Override
	public Charge mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
		Charge charge = new Charge();

		charge.setChargeId(rs.getLong("charge_id"));
		charge.setChargeName(rs.getString("name"));
		charge.setAmount(rs.getBigDecimal("amount"));
		charge.setStartDate((rs.getDate("start_Date").toLocalDate()));

		Date date = rs.getDate("end_Date");
		charge.setEndDate((date != null) ? date.toLocalDate() : null);
		
		charge.setCreatedAt(rs.getTimestamp("created_at"));
		charge.setModifiedAt(rs.getTimestamp("modified_at"));

		return charge;
	}
}
