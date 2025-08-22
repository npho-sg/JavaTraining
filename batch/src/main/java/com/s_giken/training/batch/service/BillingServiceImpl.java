package com.s_giken.training.batch.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.s_giken.training.batch.repository.BillingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {
	private final BillingRepository billingRepository;

	@Override
	public boolean isConfirmed(String ym) {

		return billingRepository.isConfirmed(ym);
	}

	@Override
	public void resetStatus(String ym) {

		try {
			billingRepository.deleteByMonth(ym);
			billingRepository.insertStatus(ym);
			logger.info("請求状況の更新に成功しました");
		} catch (Exception e) {
			logger.error("コミットに失敗しました" + e.getMessage());
			System.exit(1); 
		}
	}
}