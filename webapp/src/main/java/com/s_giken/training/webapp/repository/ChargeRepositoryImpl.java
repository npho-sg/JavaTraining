package com.s_giken.training.webapp.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChargeRepositoryImpl implements ChargeRepository {

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Charge> rowMapper;

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
	    		if(li.get("end_date") == null) {
	    			charge.setEndDate(null);
	    		}else {
	    			charge.setEndDate(((java.sql.Date)li.get("end_date")).toLocalDate());
	    		}
	    		charge.setCreatedAt((Timestamp)li.get("created_at"));
	    		charge.setModifiedAt((Timestamp)li.get("modified_at"));
	    		result.add(charge);
	    	}
	    		
		return result;
	}
	
	@Override
	public Optional<Charge> findByChargeId(Long chargeId){
		
		String sql = "SELECT * FROM T_CHARGE WHERE charge_id = ?";
		Object[] args = { chargeId };
		int[] argTypes = { Types.BIGINT };

try {
        Charge charge = jdbcTemplate.queryForObject(sql, args, argTypes, rowMapper);
        return Optional.of(charge);
    } catch (org.springframework.dao.EmptyResultDataAccessException e) {
        return Optional.empty();
    }

	}
	
	@Override
    public int add(Charge charge) {
        Long chargeId = charge.getChargeId();
        if (chargeId == null) {
            chargeId = jdbcTemplate.queryForObject("SELECT NEXT VALUE FOR t_charge_seq", Long.class);
            charge.setChargeId(chargeId);
        }

        String sql = """
                        INSERT INTO T_CHARGE (charge_id, name, amount, start_date, end_date, created_at, modified_at)
                        VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                """;
        int processed_count = jdbcTemplate.update(
           
        		sql,
                chargeId,
                charge.getChargeName(),
                charge.getAmount(),
                charge.getStartDate(),
                charge.getEndDate());

        return processed_count;
    }

    @Override
    public int update(Charge charge) {
        String sql = """
                    UPDATE T_CHARGE
                    SET
                        name = ?,
                        amount = ?,
                        start_date = ?,
                        end_date = ?,
                        modified_at = CURRENT_TIMESTAMP
                    WHERE charge_id = ?
                """;
        int processed_count = jdbcTemplate.update(
                sql,
                charge.getChargeName(),
                charge.getAmount(),
                charge.getStartDate(),
                charge.getEndDate(),
                charge.getChargeId());

        return processed_count;
    }

    @Override
    public int deleteByChargeId(Long chargeId) {
        String sql = "DELETE FROM T_CHARGE WHERE charge_id = ?";

        int processed_count = jdbcTemplate.update(sql, chargeId);

        return processed_count;
    }

}
