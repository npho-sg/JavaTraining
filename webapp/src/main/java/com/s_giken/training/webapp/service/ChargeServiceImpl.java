package com.s_giken.training.webapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;
import com.s_giken.training.webapp.repository.ChargeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChargeServiceImpl implements ChargeService {

	private final ChargeRepository chargeRepository;
	
	@Override
	public List<Charge> findByChargeName(ChargeSearchForm searchName) {

		return chargeRepository.findByChargeNameLike(searchName);
	}

}
