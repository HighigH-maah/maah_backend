package com.shinhan.maahproject.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.AccountChangeDTO;
import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.VirtualCardInfoDTO;
import com.shinhan.maahproject.service.HICardDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HiCardDetailRestController {
	
	@Autowired
	HICardDetailService hdService;
	
	@PostMapping(value="/getTempCard.do", consumes = "application/json")
	public VirtualCardInfoDTO getVirtualCard(@RequestBody MemberDTO memberId) {
		VirtualCardInfoDTO vCard = hdService.getVirtualCardInfo(memberId.getMemberId());
		return vCard;
	}
	
	@PostMapping(value="/getAccountInfo.do", consumes = "application/json")
	public AccountChangeDTO getAccountInfo(@RequestBody MemberDTO memberId) {
		AccountChangeDTO hiCard = hdService.getHiCardInfo(memberId.getMemberId());
		return hiCard;
	}
}
