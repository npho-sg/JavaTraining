package com.s_giken.training.webapp.repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChargeRepositoryImpl implements ChargeRepository {
	//jdbcを利用する為
	private final JdbcTemplate jdbcTemplate;
	//マッパーを利用するため
	private final RowMapper<Charge> rowMapper;

	/**料金名の部分一致検索
	 * 
	 * @param 検索条件
	 * @return 料金リスト
	 */
	@Override
	public List<Charge> findByChargeNameLike(ChargeSearchForm form) {

		String column = form.getSortColumn();
		String option = form.getSortOperation();

		String sql = "SELECT * FROM T_CHARGE WHERE name like ? ORDER BY " + column + " " + option;
		Object[] args = { "%" + form.getChargeName() + "%" };
		int[] argTypes = { Types.VARCHAR };

		List<Charge> result = jdbcTemplate.query(sql, args, argTypes, rowMapper);

		return result;
	}

	/**料金IDによる検索
	 * 
	 * @param 料金ID
	 * @return 料金情報一件
	 */
	@Override
	public Optional<Charge> findByChargeId(Long chargeId) {

		String sql = "SELECT * FROM T_CHARGE WHERE charge_id = ?";
		Object[] args = { chargeId };
		int[] argTypes = { Types.BIGINT };

		try {
			Charge charge = jdbcTemplate.queryForObject(sql, args, argTypes, rowMapper);
			return Optional.of(charge);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}

	}

	/**料金情報の追加
	 * 
	 * @param 料金クラス
	 * @return 実行した件数
	 */
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

	/**料金情報の更新
	 * 
	 * @param 料金クラス
	 * @return 実行した件数
	 */
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

	/**料金IDによる料金情報削除
	 * 
	 * @param 料金ID
	 * @return 実行した件数
	 */
	@Override
	public int deleteByChargeId(Long chargeId) {
		String sql = "DELETE FROM T_CHARGE WHERE charge_id = ?";

		int processed_count = jdbcTemplate.update(sql, chargeId);

		return processed_count;
	}

}
