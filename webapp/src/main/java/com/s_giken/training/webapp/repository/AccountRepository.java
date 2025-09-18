package com.s_giken.training.webapp.repository;

import com.s_giken.training.webapp.model.entity.Account;

public interface AccountRepository {
	/**ログイン時に
	 * 
	 * @param name ユーザーネーム
	 * @return アカウントクラス
	 */
	public Account findByUserName(String name);
	
}
