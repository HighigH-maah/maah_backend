package com.shinhan.maahproject.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.VirtualCardInfoDTO;
import com.shinhan.maahproject.service.MemberService;
import com.shinhan.maahproject.service.HICardDetailService;

import lombok.extern.slf4j.Slf4j;

//@CrossOrigin
@RestController
@Slf4j
public class MemberRestController {
	@Autowired
	MemberService mService;
	
	@PostMapping(value="/member.do", consumes = "application/json")
	public MemberDTO getMember(@RequestBody MemberDTO memberId) {
		
		MemberDTO member = mService.getMember(memberId.getMemberId());
		log.info(member.toString());

		return member;
	}	
}
