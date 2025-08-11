package com.s_giken.training.webapp.service;

import java.util.List;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;

public interface ChargeService {
	
	public List<Charge> findByChargeName(ChargeSearchForm form);

}
