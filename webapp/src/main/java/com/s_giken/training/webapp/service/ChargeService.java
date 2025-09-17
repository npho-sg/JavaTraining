package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;

//料金管理機能のサービスインターフェース
public interface ChargeService {
	/*料金名の部分一致による料金情報の検索
	 * 値を入力しなければ全件検索として動作する
	 */
	public List<Charge> findByChargeName(ChargeSearchForm form);
	//料金IDによる料金情報の検索
	public Optional<Charge> findByChargeId(Long chargeId);
	//料金情報の追加
	public void add(Charge charge);
	//料金情報の編集
    public void update(Charge charge);
    //料金情報IDによる料金情報の削除
    public void deleteById(Long chargeId);

}
