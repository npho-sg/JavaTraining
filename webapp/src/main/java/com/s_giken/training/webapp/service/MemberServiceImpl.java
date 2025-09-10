package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.s_giken.training.webapp.exception.AttributeErrorException;
import com.s_giken.training.webapp.model.entity.Member;
import com.s_giken.training.webapp.model.entity.MemberSearchForm;
import com.s_giken.training.webapp.repository.MemberRepository;

/**
 * 加入者管理機能のサービスクラス(実態クラス)
 */
@Service
public class MemberServiceImpl implements MemberService {
	private MemberRepository memberRepository;

	/**
	 * 加入者管理機能のサービスクラスのコンストラクタ
	 * 
	 * @param memberRepository 加入者管理機能のリポジトリクラス(SpringのDIコンテナから渡される)
	 */
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	/**
	 * 加入者を全件取得する
	 * 
	 * @return 全加入者情報
	 */
	@Override
	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	/**
	 * 加入者を1件取得する
	 * 
	 * @param memberId 加入者ID
	 * @return 加入者IDに一致した加入者情報
	 */
	@Override
	public Optional<Member> findById(Long memberId) {
		return memberRepository.findById(memberId);
	}

	/**
	 * 加入者を条件検索する
	 * 
	 * @param memberSearchCondition 加入者検索条件
	 * @return 条件に一致した加入者情報
	 */
	@Override
	public List<Member> findByConditions(MemberSearchForm form) {

		Map<String, String> columnMap = Map.of(
				"1", "member_id",
				"2", "mail",
				"3", "name",
				"4", "start_date",
				"5", "end_date",
				"6", "payment_method");

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

		return memberRepository.findByMailAndNameLike(form);
	}

	/**
	 * 加入者を登録する
	 *
	 * @param member 登録する加入者情報。 memberIdが Null であること。
	 */
	@Override
	public void add(Member member) {
		if (member.getMemberId() != null) {
			throw new AttributeErrorException("加入者IDが指定されていると登録できません。");
		}
		memberRepository.add(member);
	}

	/**
	 * 加入者情報を更新する
	 * 
	 * @param member 更新する加入者情報。memberId が NULL でないこと
	 */
	@Override
	public void update(Member member) {
		if (member.getMemberId() == null) {
			throw new AttributeErrorException("加入者IDが指定されていません。");
		}
		memberRepository.update(member);
	}

	/**
	 * 加入者を先所する
	 * 
	 * @param memberId 加入者情報のID
	 */
	@Override
	public void deleteById(Long memberId) {
		memberRepository.deleteById(memberId);
	}
}
