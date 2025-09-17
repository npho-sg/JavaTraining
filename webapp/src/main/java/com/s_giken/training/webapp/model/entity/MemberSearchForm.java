package com.s_giken.training.webapp.model.entity;

import jakarta.validation.constraints.Max;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // メンバー変数に対するゲッター・セッターを自動生成
@NoArgsConstructor // 引数のないコンストラクタを自動生成
@AllArgsConstructor // 全てのメンバ変数に対する引数を持つコンストラクタを自動生成
public class MemberSearchForm {
    //メールアドレス
	@Max(value=255, message="最大が255文字です。")
    private String mail;
	//氏名
    @Max(value=31, message="最大31文字です。")
    private String name;
    //並べ替え項目
    private String sortColumn;
    //並べ替え順
    private String sortOperation;
}
