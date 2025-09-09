package com.s_giken.training.webapp.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.s_giken.training.webapp.model.entity.Charge;

public interface ChargeSortRepository extends JpaRepository<Charge, Long>{
	
	public List<Charge> findByChargeNameContaining(String chargeName, Sort sort);

}