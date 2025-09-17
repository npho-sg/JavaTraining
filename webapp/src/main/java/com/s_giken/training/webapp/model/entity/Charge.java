package com.s_giken.training.webapp.model.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Charge {
	//料金ID
	@Nullable
	private Long chargeId;
	//料金名
	@NotBlank
	private String chargeName;
	//金額
	@NotNull
	@DecimalMin(value = "0")
	@DecimalMax(value = "999999999")
	private BigDecimal amount;
	//適用開始日
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
	private LocalDate startDate;
	//適用終了日
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Nullable
	private LocalDate endDate;
	//レコード作成日
	@Nullable
	private Timestamp createdAt;
	//レコード更新日
	@Nullable
	private Timestamp modifiedAt;

}
