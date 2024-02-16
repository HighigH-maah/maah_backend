package com.shinhan.maahproject.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.ByCardBenefitsDTO;
import com.shinhan.maahproject.dto.ByCardDetailDTO;
import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.service.ByCardDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ByCardDetailRestController {

	@Autowired
	ByCardDetailService bdService;
	
	@PostMapping(value="/getByCardInfo.do", consumes = "application/json")
	public ByCardDetailDTO getByCardInfo(@RequestBody MemberDTO memberId) {
		ByCardDetailDTO byCardInfo = bdService.getByCardInfo(memberId.getMemberId());
		return byCardInfo;
	}
	
	@PostMapping(value="/getAllByCardBenefits.do", consumes = "application/json")
	public Map<Integer, List<ByCardBenefitsDTO>> getAllByCardBenefits(@RequestBody MemberDTO memberId) {
		Map<Integer, List<ByCardBenefitsDTO>> byCardBenefits = bdService.getAllByCardBenefits(memberId.getMemberId());
		return byCardBenefits;
	}
	
//	@PostMapping(value="/getByCardBenefits.do", consumes = "application/json")
//	public ByCardBenefitsDTO getByCardBenefits(@RequestBody MemberDTO memberId) {
//		ByCardBenefitsDTO byCardBenefits = bdService.getByCardBenefits(memberId.getMemberId());
//		return byCardBenefits;
//	}
}
