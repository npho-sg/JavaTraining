package com.s_giken.training.webapp.repository;

import java.util.List;
import java.util.Optional;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;

public interface ChargeRepository {
	/**料金名による料金情報検索
	 * 値を入力しない場合は全件検索として作動
	 * 
	 * @param form 検索条件の値
	 * @return 料金のリスト
	 */
	public List<Charge> findByChargeNameLike(ChargeSearchForm form);
	
	/**料金IDによる料金情報の検索
	 * 値を入力しなかった場合は全件検索として動作する
	 * 
	 * @param chargeId 料金ID
	 * @return 料金情報一件
	 */
	public Optional<Charge> findByChargeId(Long chargeId);
	
	/**料金情報新規追加
	 * 
	 * @param charge 料金クラス
	 * @return 追加した件数
	 */
	public int add(Charge charge);
	/**料金情報編集
	 * 
	 * @param charge 料金クラス
	 * @return 編集した件数
	 */
	public int update(Charge charge);
	/**料金IDによる料金情報削除
	 * 
	 * @param chargeid 料金IS
	 * @return 削除した件数
	 */
	public int deleteByChargeId(Long chargeid);

}
