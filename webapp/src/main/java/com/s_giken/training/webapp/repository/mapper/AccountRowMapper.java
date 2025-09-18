package com.s_giken.training.webapp.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.annotation.Nonnull;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.s_giken.training.webapp.model.entity.Account;

@Component
public class AccountRowMapper implements RowMapper<Account> {

	/**マッピング処理を行うメソッド
	 * 
	 * @param rs     データベースからのレコードセット
	 * @param rowNum 処理行数
	 * @return Acountオブジェクト
	 */
	@Override
	public Account mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {

		Account acount = new Account();

		acount.setName(rs.getString("name"));
		acount.setPassword(rs.getString("password"));
		acount.setCreatedAt(rs.getTimestamp("created_at"));
		acount.setModifiedAt(rs.getTimestamp("modified_at"));

		return acount;
	}

}
