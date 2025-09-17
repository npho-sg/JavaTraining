package com.s_giken.training.webapp.model.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.s_giken.training.webapp.model.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	//加入者ID
    @Nullable
    private Long memberId;
    //メールアドレス
    @NotBlank
    private String mail;
    //氏名
    @NotBlank
    private String name;
    //住所
    @NotBlank
    private String address;
    //加入日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate startDate;
    //解約日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Nullable
    private LocalDate endDate;
    //支払方法
    @NotNull
    private PaymentMethod paymentMethod;
    //レコード作成日
    @Nullable
    private Timestamp createdAt;
    //レコード更新日
    @Nullable
    private Timestamp modifiedAt;
}
