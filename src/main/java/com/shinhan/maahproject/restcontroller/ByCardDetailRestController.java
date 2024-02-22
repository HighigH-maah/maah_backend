package com.shinhan.maahproject.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.AccountChangeDTO;
import com.shinhan.maahproject.dto.AccountCheckDTO;
import com.shinhan.maahproject.dto.ByCardBenefitsDTO;
import com.shinhan.maahproject.dto.ByCardDetailDTO;
import com.shinhan.maahproject.dto.ByCardInfoChangeDTO;
import com.shinhan.maahproject.dto.CardHistoryDTO;
import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.service.ByCardDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ByCardDetailRestController {

	@Autowired
	ByCardDetailService bdService;
	
	//By:Card 상품 해택
	@GetMapping("/byCardBenefits/{byCardCode}.do")
	public List<ByCardBenefitsDTO> getByCardBenefits(@PathVariable("byCardCode") int byCardCode) {
		List<ByCardBenefitsDTO> byCardInfo = bdService.getByCardBenefits(byCardCode);
		return byCardInfo;
	}
	
	//By:Card 상품
	@GetMapping("/byCardDetail/{byCardCode}.do")
	public ByCardDetailDTO getByCardInfo(@PathVariable("byCardCode") int byCardCode) {
		ByCardDetailDTO byCardInfo = bdService.getByCardInfo(byCardCode);
		return byCardInfo;
	}
	
	//나의 By:Card
	@PostMapping(value="/isConnectHiOrNot.do", consumes = "application/json")
	public int isConnectHiOrNot(@RequestBody Map<String, String> data) {
		int isConnectHiOrNot = bdService.isConnectHiOrNot(data.get("memberId"), data.get("memberByNumber"));
		return isConnectHiOrNot;
	}
	
	@PostMapping(value="/getMyBycardCode.do", consumes = "application/json")
	public int getMyBycardCode(@RequestBody Map<String, String> data) {
		int byCardCode = bdService.getMyBycardCode(data.get("memberId"), data.get("memberByNumber"));
		return byCardCode;
	}
	
	@PostMapping(value="/getBycardHistory.do", consumes = "application/json")
	public Map<Integer, List<CardHistoryDTO>> getBycardHistory(@RequestBody MemberDTO memberId) {
		Map<Integer, List<CardHistoryDTO>> byCardHistory = bdService.getBycardHistory(memberId.getMemberId());
		return byCardHistory;
	}
	
	@PostMapping(value="/getByCardAccountInfo.do", consumes = "application/json")
	public AccountChangeDTO getAccountInfo(@RequestBody Map<String, String> data) {
		AccountChangeDTO byCardAccountInfo = bdService.getByCardAccountInfo(data.get("memberId"), data.get("memberByNumber"));
		return byCardAccountInfo;
	}
	
	@PostMapping(value="/getAllByCardInfo.do", consumes = "application/json")
	public Map<Integer, List<ByCardDetailDTO>> getMyByCardInfo(@RequestBody MemberDTO memberId) {
		Map<Integer, List<ByCardDetailDTO>> byCardInfo = bdService.getAllByCardInfo(memberId.getMemberId());
		return byCardInfo;
	}
	
	@PostMapping(value="/getAllByCardBenefits.do", consumes = "application/json")
	public Map<Integer, List<ByCardBenefitsDTO>> getAllByCardBenefits(@RequestBody MemberDTO memberId) {
		Map<Integer, List<ByCardBenefitsDTO>> byCardBenefits = bdService.getAllByCardBenefits(memberId.getMemberId());
		return byCardBenefits;
	}
	
	
	
	
	
	
	
	
	
	
	@PostMapping(value="updateByCardInfo.do", consumes = "application/json")
	public int updateByCardInfo(@RequestBody ByCardInfoChangeDTO byInfoChange){
		return bdService.updateByCardInfo(byInfoChange);
	}
}
