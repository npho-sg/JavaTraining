package com.s_giken.training.webapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AcountServiceImpl implements AcountService, UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		
		
		return null;
	}

}
