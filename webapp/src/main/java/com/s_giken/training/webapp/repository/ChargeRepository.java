package com.s_giken.training.webapp.repository;

import java.util.List;
import java.util.Optional;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;

public interface ChargeRepository {
	/*料金名による料金情報検索
	 * 値を入力しない場合は全件検索として作動
	 */
	public List<Charge> findByChargeNameLike(ChargeSearchForm form);
	/*料金IDによる料金情報の検索
	 * 値を入力しなかった場合は全件検索として動作する
	 */
	public Optional<Charge> findByChargeId(Long chargeId);
	//料金情報の追加
	public int add(Charge charge);
	//料金情報の更新
	public int update(Charge charge);
	//料金IDによる料金情報削除
	public int deleteByChargeId(Long chargeid);

}
