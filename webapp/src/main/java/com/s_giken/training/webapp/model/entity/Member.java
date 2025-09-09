package com.s_giken.training.webapp.model.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.s_giken.training.webapp.model.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "T_MEMBER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@Id
    @Nullable
    @Column(name = "MEMBER_ID")
    private Long memberId;
    
    @NotBlank
    @Column(name = "MAIL")
    private String mail;

    @NotBlank
    @Column(name = "NAME")
    private String name;

    @NotBlank
    @Column(name = "ADDRESS")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Column(name = "START_DATE")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Nullable
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @NotNull
    @Column(name = "PAYMENT_METHOD")
    private PaymentMethod paymentMethod;

    @Nullable
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;

    @Nullable
    @Column(name = "MODIFIED_ATs")
    private Timestamp modifiedAt;
}
