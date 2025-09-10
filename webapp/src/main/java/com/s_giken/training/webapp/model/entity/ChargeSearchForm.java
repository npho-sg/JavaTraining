package com.s_giken.training.webapp.model.entity;

import jakarta.validation.constraints.Max;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // メンバー変数に対するゲッター・セッターを自動生成
@NoArgsConstructor // 引数のないコンストラクタを自動生成
@AllArgsConstructor // 全てのメンバ変数に対する引数を持つコンストラクタを自動生成
public class ChargeSearchForm {
	
	@Max(value=127, message="最大が127文字です。")
	private String chargeName;

	private String parm;
	private String option;
}
