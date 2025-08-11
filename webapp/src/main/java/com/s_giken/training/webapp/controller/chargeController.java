package com.s_giken.training.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.s_giken.training.webapp.controller.editor.PaymentMethodEditorSupport;
import com.s_giken.training.webapp.model.PaymentMethod;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;
import com.s_giken.training.webapp.service.ChargeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/charge")
public class chargeController {
	
	private final ChargeService chargeService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// PaymentMethod列挙型
		// リクエスト → PaymentMethod : Paymentmethod.fromCodeメソッドを利用して PaymentMethod列挙型へ変換
		// Paymentmethod → リクエスト : Paymentmethod.getCodeメソッドを利用して、数値の文字列へ変換
		binder.registerCustomEditor(PaymentMethod.class, new PaymentMethodEditorSupport());
	}
	
	@GetMapping("/search")
	public String serchCharge(Model model) {
		var chargeSearchForm = new ChargeSearchForm();
		model.addAttribute("chargeSearchForm", chargeSearchForm);
		return "charge_search";
	}
	
	@PostMapping("/search")
	public String searchChargeResult(
			@ModelAttribute("chargeSearchForm") ChargeSearchForm chargeSearchForm,
			Model model) {
		var result = chargeService.findByChargeName(chargeSearchForm);
		model.addAttribute("result", result);
		return "charge_search_result";
	}

}
