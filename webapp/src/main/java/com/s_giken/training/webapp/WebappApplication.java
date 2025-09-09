package com.s_giken.training.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Bootの起動クラス
 */
@SpringBootApplication

@EnableJpaRepositories(basePackages = "com.s_giken.training.webapp.repository.jpa")
@EnableJdbcRepositories(basePackages = "com.s_giken.training.webapp.repository.jdbc")

public class WebappApplication {
	/**
	 * Spring Bootの起動メソッド
	 * 
	 * @param args コマンドライン引数
	 */
	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}
}
