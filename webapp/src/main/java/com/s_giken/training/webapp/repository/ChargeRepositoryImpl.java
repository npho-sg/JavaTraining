package com.s_giken.training.webapp.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChargeRepositoryImpl implements ChargeRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<Charge> findByChargeNameLike(ChargeSearchForm chargeName){
	
	    	String sql = "SELECT * FROM T_CHARGE WHERE name like ?";
	    	String p = "%" + chargeName.getChargeName() + "%";
	    	
	    	List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, p);
	    	
	    	List<Charge> result = new ArrayList<>();
	    	for(Map<String,Object> li : list) {
	    		Charge charge = new Charge();
	    		charge.setChargeId((long)li.get("charge_id"));
	    		charge.setChargeName((String)li.get("name"));
	    		charge.setAmount((BigDecimal)li.get("amount"));
	    		charge.setStartDate(((java.sql.Date)li.get("start_date")).toLocalDate());
	    		charge.setEndDate(((java.sql.Date)li.get("end_date")).toLocalDate());
	    		charge.setCreatedAt((Timestamp)li.get("created_at"));
	    		charge.setModifiedAt((Timestamp)li.get("modified_at"));
	    		result.add(charge);
	    	}
		return result;
	}

}
