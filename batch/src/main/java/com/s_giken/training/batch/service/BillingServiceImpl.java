package com.s_giken.training.batch.service;

import org.springframework.stereotype.Service;

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
	public boolean resetStatus(String ym) {

		try {
			billingRepository.deleteByMonth(ym);
			billingRepository.insertStatus(ym);
			return true;
		} catch (Exception e) {
			logger.error("更新に失敗しました",e);
			return false;
		}
	}
}