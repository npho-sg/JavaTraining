package com.s_giken.training.webapp.repository;

import java.util.List;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;

public interface ChargeRepository {
	
	public List<Charge> findByChargeNameLike(ChargeSearchForm chargeName);
	
}
