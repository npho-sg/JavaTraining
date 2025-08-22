package com.s_giken.training.batch;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.s_giken.training.batch.entity.Billing_Status;
import com.s_giken.training.batch.repository.BillingRepository;
import com.s_giken.training.batch.service.BillingService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
public class BatchApplication implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(BatchApplication.class);
	private final JdbcTemplate jdbcTemplate;
	private final BillingService billingService;

	/**
	 * SpringBoot エントリポイント
	 * 
	 * @param args コマンドライン引数
	 */
	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param jdbcTemplate SpringBootから注入される JdbcTemplate オブジェクト
	 */
	public BatchApplication(JdbcTemplate jdbcTemplate, BillingService billingService) {
		this.jdbcTemplate = jdbcTemplate;
		this.billingService = billingService;
	}

	/**
	 * コマンドラインプログラムのエントリ―ポイント
	 * 
	 * @param args コマンドライン引数
	 */
	@Override
	public void run(String... args) throws RuntimeException {
		logger.info("-".repeat(40));

		// TODO: ここにバッチ処理のコードを記述する
		if (args.length == 0 || args.length >= 2 || !args[0].matches("\\d{6}")) {
			logger.error("不正な値です。");
			System.exit(1);
		}
		// - データベースからデータを取得する

		String targetYm = args[0];
		logger.info(targetYm + "の請求状況を取得します");
		if (billingService.isConfirmed(targetYm)) {
			logger.info("対象年月 {} は確定済みです。", targetYm);
			System.exit(0);
		}
		
		// - データを加工する
		// - 加工したデータをデータベースに登録する
		logger.info("請求状況を更新します");
		if(billingService.resetStatus(targetYm) == false){
			System.exit(1);
		}
		logger.info("請求状況の更新に成功しました");
		// - データを加工する
		// - 加工したデータをデータベースに登録する
	}

	// ダミーコード
	// 削除してください。
	Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM T_MEMBER", Integer.class);if(count!=null)
	{
		logger.info("加入者数:" + count.toString());
	}else
	{
		logger.error("加入者数を取得できませんでした。");
	}
	// ダミーコードここまで

	logger.info("-".repeat(40));
}}
