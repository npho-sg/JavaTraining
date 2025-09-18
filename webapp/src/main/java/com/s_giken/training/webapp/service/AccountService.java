package com.s_giken.training.webapp.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface AccountService {
	
	public UserDetails loadUserByUsername(String name);
}