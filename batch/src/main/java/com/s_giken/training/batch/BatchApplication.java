package com.s_giken.training.batch;

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

		// TODO: ここにバッチ処理のコードを記述する
		if (args.length == 0 || args.length >= 2 || !args[0].matches("\\d{6}")) {
			logger.error("不正な値です");
			System.exit(1);
		}
		// - データベースからデータを取得する

		String targetYm = args[0];
		String ym = targetYm.substring(0, 4) + "年" + targetYm.substring(5, 6) + "月";

		logger.info(ym + "分の請求情報を確認しています。");
		if (billingService.isConfirmed(targetYm).orElse(0) == 1) {
			logger.info(ym + "分は確定済みです。");
			System.exit(0);
		}

		// - データを加工する
		// - 加工したデータをデータベースに登録する
		if (billingService.resetStatus(targetYm, ym) == false) {
			logger.error(ym + "分の請求ステータス情報を");
			System.exit(1);
		}

		// ダミーコード
		// 削除してください。
		Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM T_MEMBER", Integer.class);
		if (count != null) {
			logger.info("加入者数:" + count.toString());
		} else {
			logger.error("加入者数を取得できませんでした。");
		}
		// ダミーコードここまで

		logger.info("-".repeat(40));
	}
}
