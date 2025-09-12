package com.s_giken.training.webapp.model.entity;

import java.sql.Timestamp;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Acount {
	
	@NotNull
	private String name;
	@NotNull
	private String password;
	@Nullable
	private Timestamp createdAt;
	@Nullable
	private Timestamp modifiedAt;

}
