package com.s_giken.training.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.s_giken.training.webapp.controller.editor.PaymentMethodEditorSupport;
import com.s_giken.training.webapp.exception.NotFoundException;
import com.s_giken.training.webapp.model.PaymentMethod;
import com.s_giken.training.webapp.model.entity.Charge;
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
	
	//追加機能
	@GetMapping("/add")
	public String addCharge(Model model) {
		
		var charge = new Charge();
		model.addAttribute("isAddMode", true);
		model.addAttribute("charge", charge);
		return "charge_edit";
	}
	
	@PostMapping("/add")
	@Transactional
	public String addChargeComfirm(@Validated Charge charge,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "charge_edit";
		}
		chargeService.add(charge);
		redirectAttributes.addFlashAttribute("message", "保存しました。");
		return "redirect:/charge/edit/" + charge.getChargeId();
	}
	
	//編集機能
	@GetMapping("/edit/{id}")
	public String editCharge(
			@PathVariable("id") Long chargeId,
			Model model) {
		var charge = chargeService.findByChargeId(chargeId);
		if (!charge.isPresent()) {
			throw new NotFoundException(String.format("指定したchargeId(%d)の料金情報が存在しません。", chargeId));
		}
		model.addAttribute("isAddMode", false);
		model.addAttribute("charge", charge.get());
		return "charge_edit";
	
	}
	
	@PostMapping("/update")
	@Transactional
	public String saveCharge(
			@Validated Charge charge,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "charge_edit";
		}
		chargeService.update(charge);
		redirectAttributes.addFlashAttribute("message", "保存しました。");
		return "redirect:/charge/edit/" + charge.getChargeId();
	}
	
	@GetMapping("/delete/{id}")
	@Transactional
	public String deleteMember(
			@PathVariable("id") Long chargeId,
			RedirectAttributes redirectAttributes) {
		var charge = chargeService.findByChargeId(chargeId);
		if (!charge.isPresent()) {
			throw new NotFoundException(String.format("指定したchargeId(%d)の加入者情報が存在しません。", chargeId));
		}

		chargeService.deleteById(chargeId);
		redirectAttributes.addFlashAttribute("message", "削除しました。");
		return "redirect:/member/search";
	}
}
