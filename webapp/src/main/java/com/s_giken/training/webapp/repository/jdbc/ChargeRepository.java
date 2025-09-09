package com.s_giken.training.webapp.repository.jdbc;

import java.util.Optional;

import com.s_giken.training.webapp.model.entity.Charge;

public interface ChargeRepository {

	public Optional<Charge> findByChargeId(Long chargeId);

	public int add(Charge charge);

	public int update(Charge charge);

	public int deleteByChargeId(Long chargeid);

}
