package com.s_giken.training.webapp.repository;

import java.util.List;
import java.util.Optional;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;

public interface ChargeRepository {

	public List<Charge> findByChargeNameLike(ChargeSearchForm chargeName);

	public Optional<Charge> findByChargeId(Long chargeId);

	public int add(Charge charge);

	public int update(Charge charge);

	public int deleteByChargeId(Long chargeid);

}
