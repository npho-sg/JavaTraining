package com.s_giken.training.batch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import com.s_giken.training.batch.service.BillingService;

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

		if (args.length == 0) {
			logger.error("不正な値です。対象年月を１つ選択してください。");
			System.exit(1);
		} else if (args.length >= 2) {
			logger.error("不正な値です。対象年月は１つまでです。");
			System.exit(1);
		} else if (!args[0].matches("\\d{6}")) {
			logger.error("不正な値です。６文字入力してください。");
			System.exit(1);
		}

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDate date = LocalDate.parse(args[0] +"01", formatter);
		} catch (DateTimeParseException e) {
			logger.error("不正な値です。１月から１２月の範囲で指定してください。");
			System.exit(1);
		}
		// - データベースからデータを取得する

		String targetYm = args[0];
		String ym = targetYm.substring(0, 4) + "年" + targetYm.substring(4, 6) + "月";

		logger.info(ym + "分の請求情報を確認しています。");
		if (billingService.isConfirmed(targetYm).orElse(0) == 1) {
			logger.info(ym + "分は確定済みです。");
			System.exit(0);
		}

		// - データを加工する
		// - 加工したデータをデータベースに登録する
		if (billingService.resetStatus(targetYm, ym) == false) {
			logger.error(ym + "分の請求ステータス情報を追加できませんでした。");
			System.exit(1);
		}
		if(billingService.resetDataAndDetail(targetYm, ym) == false) {
			logger.error(ym + "分の請求データを追加できませんでした。");
			logger.error(ym + "分の請求明細データを追加できませんでした。");
			System.exit(1);
		}
		logger.info("-".repeat(40));
	}
}
