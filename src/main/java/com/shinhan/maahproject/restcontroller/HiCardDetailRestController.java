package com.shinhan.maahproject.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.AccountChangeDTO;
import com.shinhan.maahproject.dto.HiCardDetailDTO;
import com.shinhan.maahproject.dto.HiCardHistoryDTO;
import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.VirtualCardInfoDTO;
import com.shinhan.maahproject.service.HICardDetailService;
import com.shinhan.maahproject.vo.BankVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HiCardDetailRestController {
	
	@Autowired
	HICardDetailService hdService;
	
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
	
	@PostMapping(value="/getHiCardInfo.do", consumes = "application/json")
	public HiCardDetailDTO getHiCardInfo(@RequestBody MemberDTO memberId) {
		HiCardDetailDTO hiCardInfo = hdService.getHiCardInfo(memberId.getMemberId());
		return hiCardInfo;
	}
	
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
	
}
