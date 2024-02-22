package com.shinhan.maahproject.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.CardApplyDTO;
import com.shinhan.maahproject.service.CardApplicationService;
import com.shinhan.maahproject.vo.BankVO;
import com.shinhan.maahproject.vo.HiCardImageVO;

@RestController
public class CardApplicationController {

	@Autowired
	CardApplicationService service;
	
	@PostMapping("/getHiCardImages")
	public Map<String, Object> getHicardDesigns(@RequestBody Map<String, String> data) {	
		return service.getHicardDesigns(data.get("member_id").toString());
	}
	
	@PostMapping(value="/cardApply", consumes = "application/json")
	public HiCardImageVO cardApply(@RequestBody CardApplyDTO cardApply) {
		return service.applyCard(cardApply);
	}
	
	@GetMapping("/getCardApplyBankCode")
	public List<BankVO> getCardApplyBankCode() {
		return service.getCardApplyBankCode();
	}
}
