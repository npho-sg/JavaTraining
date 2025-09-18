package com.s_giken.training.webapp.model.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements UserDetails{
	//アカウント名
	@NotNull
	private String name;
	//パスワード
	@NotNull
	private String password;
	//レコード作成日
	@Nullable
	private Timestamp createdAt;
	//レコード更新日
	@Nullable
	private Timestamp modifiedAt;
	/**ユーザー権限を返す
	 * @return ユーザー権限を取り扱うインターフェースList
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}
	/**アカウントに紐づいた形式のアカウントのゲッター
	 * @return アカウント名
	 */
	@Override
	public String getUsername() {
		return this.name;
	}
}
