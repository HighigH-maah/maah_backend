package com.shinhan.maahproject.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.AccountChangeDTO;
import com.shinhan.maahproject.dto.HiCardBenefitsDTO;
import com.shinhan.maahproject.dto.HiCardDetailDTO;
import com.shinhan.maahproject.dto.HiCardHistoryDTO;
import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.VirtualCardInfoDTO;
import com.shinhan.maahproject.service.CertificationService;
import com.shinhan.maahproject.service.HICardDetailService;
import com.shinhan.maahproject.vo.BankVO;
import com.shinhan.maahproject.vo.TempHiVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HiCardDetailRestController {
	
	@Autowired
	HICardDetailService hdService;
	
	@Autowired
	CertificationService cService;
	
	@PostMapping(value="/getVirtualCardExistOrNot.do", consumes = "application/json")
	public int getVirtualCardExistOrNot(@RequestBody MemberDTO memberId){
		int virtualCardExistOrNot = hdService.getVirtualCardExistOrNot(memberId.getMemberId());
		return virtualCardExistOrNot;
	}
	
	@PostMapping(value="/getHiCardBenefits.do", consumes = "application/json")
	public List<HiCardBenefitsDTO> getHiCardBenefits(@RequestBody MemberDTO memberId){
		List<HiCardBenefitsDTO> benefitInfo = hdService.getHiCardBenefits(memberId.getMemberId());
		return benefitInfo;
	}
	
	@GetMapping(value="/getBankName.do")
	public List<BankVO> getBankName(){
		List<BankVO> bankInfo = hdService.getBankName();
		return bankInfo;
	}
	
	@PostMapping(value="/getHicardHistory.do", consumes = "application/json")
	public List<HiCardHistoryDTO> getHicardHistory(@RequestBody MemberDTO memberId) {
		List<HiCardHistoryDTO> hiCardHistory = hdService.getHicardHistory(memberId.getMemberId());
		return hiCardHistory;
	}
	
	//주소 뒤에 카드번호 넣어야 해
	@PostMapping(value="/getHiCardInfo.do", consumes = "application/json")
	public HiCardDetailDTO getHiCardInfo(@RequestBody MemberDTO memberId) {
		HiCardDetailDTO hiCardInfo = hdService.getHiCardInfo(memberId.getMemberId());
		return hiCardInfo;
	}
	
	//주소 뒤에 카드번호 넣어야 해
//	@PostMapping(value="/getHiCardInfo.do/{cardNum}", consumes = "application/json")
//	public HiCardDetailDTO getHiCardInfo(@RequestBody MemberDTO memberId, @PathVariable("cardNum") String cardNum) {
//		HiCardDetailDTO hiCardInfo = hdService.getHiCardInfo(memberId.getMemberId(), cardNum);
//		return hiCardInfo;
//	}
	
	@PostMapping(value="/getAccountInfo.do", consumes = "application/json")
	public AccountChangeDTO getAccountInfo(@RequestBody MemberDTO memberId) {
		AccountChangeDTO hiCardAccountInfo = hdService.getHiCardAccountInfo(memberId.getMemberId());
		return hiCardAccountInfo;
	}
	
	@PostMapping(value="/getTempCard.do", consumes = "application/json")
	public VirtualCardInfoDTO getVirtualCard(@RequestBody MemberDTO memberId) {
		VirtualCardInfoDTO vCard = hdService.getVirtualCardInfo(memberId.getMemberId());
		return vCard;
	}
	
	@PostMapping(value="/getTempHiCard.do", consumes = "application/json")
	public TempHiVO getTempHiCard(@RequestBody MemberDTO memberId) {
		return cService.tempHiInsert(memberId.getMemberId());
	}
	
}
