package com.s_giken.training.webapp.repository;

import com.s_giken.training.webapp.model.entity.Acount;

public interface AcountRepository {
	
	public Acount findbyUserName(String name);
	
}
