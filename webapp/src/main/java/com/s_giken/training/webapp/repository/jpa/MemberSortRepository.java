package com.s_giken.training.webapp.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.s_giken.training.webapp.model.entity.Member;

public interface MemberSortRepository extends JpaRepository<Member, Long>{
	
	List<Member> findByMailContainingAndNameContaining(String mail, String name, Sort sort);

}
