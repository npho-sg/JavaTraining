package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.s_giken.training.webapp.exception.AttributeErrorException;
import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;
import com.s_giken.training.webapp.repository.ChargeRepository;

import lombok.RequiredArgsConstructor;

//料金管理機能のサービスクラス（実態クラス）
@Service
@RequiredArgsConstructor
public class ChargeServiceImpl implements ChargeService {

	private final ChargeRepository chargeRepository;
	
	//料金名の部分一致による料金情報の検索
	@Override
	public List<Charge> findByChargeName(ChargeSearchForm form) {

		Map<String, String> columnMap = Map.of(
				"1", "charge_id",
				"2", "name",
				"3", "start_date",
				"4", "end_date");

		String column = columnMap.get(form.getSortColumn());
		if (column == null) {
			throw new IllegalArgumentException("並べ替え項目の値が不正です。");
		}
		form.setSortColumn(column);

		Map<String, String> optionMap = Map.of(
				"up", "ASC",
				"down", "DESC");

		String option = optionMap.get(form.getSortOperation());
		if (option == null) {
			throw new IllegalArgumentException("並べ替え項目の値が不正です。");
		}
		form.setSortOperation(option);

		return chargeRepository.findByChargeNameLike(form);
	}
	//料金IDでの料金情報の検索
	@Override
	public Optional<Charge> findByChargeId(Long chargeId) {

		return chargeRepository.findByChargeId(chargeId);
	}
	//料金情報の追加
	@Override
	public void add(Charge charge) {
		if (charge.getChargeId() != null) {
			throw new AttributeErrorException("料金IDが指定されていると登録できません。");
		}
		chargeRepository.add(charge);
	}
	//料金情報の編集
	@Override
	public void update(Charge charge) {
		if (charge.getChargeId() == null) {
			throw new AttributeErrorException("料金IDが指定されていません。");
		}
		chargeRepository.update(charge);
	}
	//料金IDでの料金情報の削除
	@Override
	public void deleteById(Long chargeId) {
		chargeRepository.deleteByChargeId(chargeId);
	}

}
