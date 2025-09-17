package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;

import com.s_giken.training.webapp.model.entity.Member;
import com.s_giken.training.webapp.model.entity.MemberSearchForm;

/*
 * 【ヒント】
 * MemberService をインターフェースとして定義することで、
 * インターフェースを実体化するクラスができることを明確化する。
 */

/**
 * 加入者管理機能のサービスインターフェース
 */
public interface MemberService {
	/**加入者情報の全件検索
	 * 
	 * @return 加入者リスト
	 */
	public List<Member> findAll();
	
	/**加入者IDによる加入者情報検索
	 * 
	 * @param memberId 加入者ID
	 * @return 加入者情報一件
	 */
	public Optional<Member> findById(Long memberId);
	
	/**加入者氏名の部分一致またはメールアドレスの部分一致による加入者情報検索
	 *値を入力しなければ全件検索として動作する
	 *
	 *@param form 検索条件
	 *@retun 加入者リスト
	 */
	public List<Member> findByConditions(MemberSearchForm form);
	
	/**加入差情報の追加
	 * 
	 * @param member 加入者クラス
	 */
	public void add(Member member);
	/**加入者情報の編集
	 * 
	 * @param member 加入者クラス
	 */
	public void update(Member member);
	/**料金IDによる加入者情報の削除
	 * 
	 * @param memberId 加入者ID
	 */
	public void deleteById(Long memberId);
}
