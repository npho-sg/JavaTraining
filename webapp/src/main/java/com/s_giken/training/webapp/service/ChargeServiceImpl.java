package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.s_giken.training.webapp.exception.AttributeErrorException;
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
	
	@Override
	public Optional<Charge> findByChargeId(Long chargeId){
		
		return chargeRepository.findByChargeId(chargeId);
	}
	
	@Override
	public void add(Charge charge) {
		if (charge.getChargeId() != null) {
			throw new AttributeErrorException("料金IDが指定されていると登録できません。");
		}
		chargeRepository.add(charge);
	}

	@Override
	public void update(Charge charge) {
		if (charge.getChargeId() == null) {
			throw new AttributeErrorException("料金IDが指定されていません。");
		}
		chargeRepository.update(charge);
	}


	@Override
	public void deleteById(Long chargeId) {
		chargeRepository.deleteByChargeId(chargeId);
	}

}
