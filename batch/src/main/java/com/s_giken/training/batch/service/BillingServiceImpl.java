package com.s_giken.training.batch.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s_giken.training.batch.BatchApplication;
import com.s_giken.training.batch.repository.BillingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
	private final BillingRepository billingRepository;
	private final Logger logger = LoggerFactory.getLogger(BatchApplication.class);

	@Override
	public Optional<Integer> isConfirmed(String ym) {

		return billingRepository.getConfirmedCount(ym);
	}

	@Transactional
	@Override
	public boolean resetStatus(String ym, String ymf) {

		try {
			billingRepository.deleteByMonth(ym);
			logger.info("データベースから" + ymf + "分の未確定請求情報を削除しました。");
			logger.info(ymf + "分の請求ステータス情報を追加しています。");
			billingRepository.insertStatus(ym);
			logger.info("１件追加しました。");
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}

	}

	@Transactional
	public boolean resetDataAndDetail(String ym, String ymf) {

		try {
			logger.info(ymf + "分の請求データ情報を追加しています。");
			int result = billingRepository.insertData(ym);
			logger.info(result + "件追加しました。");
			logger.info(ymf + "分の請求明細データ情報を追加しています。");
			int result1 = billingRepository.insertDetail(ym);
			logger.info(result1 + "件追加しました。");
			
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
}