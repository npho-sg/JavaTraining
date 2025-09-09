package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;

public interface ChargeService {
	
	public List<Charge> findByNameSort(ChargeSearchForm form);
	
	public Optional<Charge> findByChargeId(Long chargeId);
	
	public void add(Charge charge);

    public void update(Charge charge);

    public void deleteById(Long chargeId);

}
