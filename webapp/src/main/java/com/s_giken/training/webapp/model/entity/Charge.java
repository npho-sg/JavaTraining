package com.s_giken.training.webapp.model.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_CHARGE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Charge {
	
	@Id
	@Nullable
	@Column(name = "CHARGE_ID")
	private Long chargeId;
	
	@Column(name = "NAME")
	@NotBlank
	private String chargeName;
	
	@NotNull
	@DecimalMin(value = "0")
	@DecimalMax(value = "999999999")
	@Column(name = "AMOUNT", precision = 9, scale = 0)
	private BigDecimal amount;
	
	@Column(name = "START_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
	private LocalDate startDate;
	
	@Column(name = "END_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Nullable
	private LocalDate endDate;
	
	@Column(name = "CREATED_AT")
	@Nullable
	private Timestamp createdAt;
	
	@Column(name = "MODIFIED_AT")
	@Nullable
	private Timestamp modifiedAt;

}
