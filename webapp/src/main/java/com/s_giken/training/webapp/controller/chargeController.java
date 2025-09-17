package com.s_giken.training.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.s_giken.training.webapp.exception.NotFoundException;
import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.model.entity.ChargeSearchForm;
import com.s_giken.training.webapp.service.ChargeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/charge")
public class chargeController {
	
	private final ChargeService chargeService;
	
	/**
	 * 料金検索ページに遷移する
	 * 
	 * @param model Thymeleafにパラメータを渡す
	 * @return 料金検索ページのテンプレート名
	 */
	@GetMapping("/search")
	public String serchCharge(Model model) {
		var chargeSearchForm = new ChargeSearchForm();
		model.addAttribute("chargeSearchForm", chargeSearchForm);
		return "charge_search";
	}
	/**
	 * 料金情報検索結果ページに遷移する
	 * 
	 * @param chargeSearchForm viewから検索条件のパラメーターを受け取る 
	 * @param model Thymeleafにパラメータを渡す為
	 * @return 料金一覧（検索結果）ページのテンプレート名
	 */
	@PostMapping("/search")
	public String searchChargeResult(
			@ModelAttribute("chargeSearchForm") ChargeSearchForm chargeSearchForm,
			Model model) {
		var result = chargeService.findByChargeName(chargeSearchForm);
		model.addAttribute("result", result);
		return "charge_search_result";
	}
	
	/**料金情報新規追加のページへ遷移する
	 * 
	 * @param model Thymeleafにパラメータを渡す為
	 * @return 料金新規追加ページのテンプレート名
	 */
	@GetMapping("/add")
	public String addCharge(Model model) {
		
		var charge = new Charge();
		model.addAttribute("isAddMode", true);
		model.addAttribute("charge", charge);
		return "charge_edit";
	}
	/**
	 * 料金情報新規追加時にデーターベースに保存せずに不正な値が出た場合は警告文を表示する
	 * 
	 * @param model Thymeleafにパラメータを渡す為
	 * @param charge 料金クラスと紐づける
	 * @param bindingResult 値が不正かどうかの確認
	 * @param redirectAttributes リダイレクト先で値を保持する為のスコープ
	 * @return エラー発生時：料金新規追加ページのテンプレート名、成功時：料金編集ページのテンプレート名へのリダイレクト
	 */
	@PostMapping("/add")
	@Transactional
	public String addChargeComfirm(
			Model model,
			@Validated Charge charge,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("isAddMode", true);
			return "charge_edit";
		}
		chargeService.add(charge);
		redirectAttributes.addFlashAttribute("message", "保存しました。");
		return "redirect:/charge/edit/" + charge.getChargeId();
	}
	
	/**料金情報編集のページに遷移する
	 * 
	 * @param chargeId 料金ID
	 * @param model Thymeleafにパラメータを渡す為
	 * @return 料金編集ページのテンプレート名
	 */
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
	/** データーベースに編集した料金情報を登録する
	 * 
	 * @param charge 料金クラスと紐づける為
	 * @param bindingResult 値が不正かどうかの確認
	 * @param redirectAttributes リダイレクト先で値を保持する為のスコープ
	 * @return エラー発生時：料金編集ページのテンプレート名、成功時：料金編集ページのテンプレート名へのリダイレクト
	 */
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
	/**料金除法を削除する
	 * 
	 * @param chargeId 料金ID
	 * @param redirectAttributes ダイレクト先で値を保持する為のスコープ
	 * @return 料金検索ページのテンプレート名へリダイレクト
	 */
	@GetMapping("/delete/{id}")
	@Transactional
	public String deleteCharge(
			@PathVariable("id") Long chargeId,
			RedirectAttributes redirectAttributes) {
		var charge = chargeService.findByChargeId(chargeId);
		if (!charge.isPresent()) {
			throw new NotFoundException(String.format("指定したchargeId(%d)の料金情報が存在しません。", chargeId));
		}

		chargeService.deleteById(chargeId);
		redirectAttributes.addFlashAttribute("message", "削除しました。");
		return "redirect:/charge/search";
	}
}
