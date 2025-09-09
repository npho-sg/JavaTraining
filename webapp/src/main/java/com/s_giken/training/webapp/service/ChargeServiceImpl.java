package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.s_giken.training.webapp.exception.AttributeErrorException;
import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;
import com.s_giken.training.webapp.repository.jdbc.ChargeRepository;
import com.s_giken.training.webapp.repository.jpa.ChargeSortRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChargeServiceImpl implements ChargeService {

	private final ChargeRepository chargeRepository;
	private final ChargeSortRepository chargeSortRepository;
	
	//Jpa利用
	public List<Charge> findByNameSort(ChargeSearchForm form){
		String chargeName = form.getChargeName();
		String parm = form.getSortColumn();
		String order = form.getSortOperation();
		

		Sort sort = null;
		if(order.equals("up")) {
			sort = Sort.by(parm).ascending();
		}else if(order.equals("down")){
			sort = Sort.by(parm).descending();
		}

		List<Charge> sortedList
			= chargeSortRepository.findByChargeNameContaining(chargeName, sort);
		
		return sortedList;
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
