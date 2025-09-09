package com.s_giken.training.webapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s_giken.training.webapp.exception.AttributeErrorException;
import com.s_giken.training.webapp.model.entity.Member;
import com.s_giken.training.webapp.model.entity.MemberSearchCondition;
import com.s_giken.training.webapp.repository.jdbc.MemberRepository;
import com.s_giken.training.webapp.repository.jpa.MemberSortRepository;

/**
 * 加入者管理機能のサービスクラス(実態クラス)
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
	private MemberRepository memberRepository;
	private MemberSortRepository memberSortRepository;

	/**
	 * 加入者管理機能のサービスクラスのコンストラクタ
	 * 
	 * @param memberRepository 加入者管理機能のリポジトリクラス(SpringのDIコンテナから渡される)
	 */
	public MemberServiceImpl(MemberRepository memberRepository, MemberSortRepository memberSortRepository) {
		this.memberRepository = memberRepository;
		this.memberSortRepository = memberSortRepository;
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
	
	//Jpa利用
	public List<Member> findToSort(MemberSearchCondition memberSearchCondition){
		String mail = memberSearchCondition.getMail();
		String name = memberSearchCondition.getName();
		String parm = memberSearchCondition.getSortColumn();
		String order = memberSearchCondition.getSortOperation();

		Sort sort = null;
		if(order.equals("up")) {
			sort = Sort.by(parm).ascending();
		}else if(order.equals("down")){
			sort = Sort.by(parm).descending();
		}

		List<Member> sortedList
			= memberSortRepository.findByMailContainingAndNameContaining(mail, name, sort);
		
		return sortedList;
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
