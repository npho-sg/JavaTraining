package com.s_giken.training.webapp.repository;

import java.sql.Types;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.s_giken.training.webapp.model.entity.Account;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
	//jdbcを利用する為
	private final JdbcTemplate jdbcTemplate;
	//マッパーを利用するため
	private final RowMapper<Account> rowMapper;
	
    /**ログイン時のアカウント名によるユーザー検索
    * @param name ユーザー名
    * @return アカウントクラス
    */
	@Override
	public Account findByUserName(String name) {
		
		Object[] args = { name };
		int[] argTypes = { Types.VARCHAR };
		
		Account acount = jdbcTemplate.queryForObject("SELECT * FROM T_ACCOUNT WHERE name = ? ", args, argTypes, rowMapper);
		
		return acount ;
	}

}
