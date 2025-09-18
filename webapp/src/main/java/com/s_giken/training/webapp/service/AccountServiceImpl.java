package com.s_giken.training.webapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.s_giken.training.webapp.model.entity.Account;
import com.s_giken.training.webapp.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {

	private final AccountRepository accountRepository;

	/**ログイン時にアカウントを検索する
	 * 
	 * @param name アカウント名
	 * @return アカウントクラス
	 */
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

		Account account = accountRepository.findByUserName(name);
		if (account == null) {
			throw new UsernameNotFoundException("一致するアカウント情報がありません");
		}
		return account;
	}

}
