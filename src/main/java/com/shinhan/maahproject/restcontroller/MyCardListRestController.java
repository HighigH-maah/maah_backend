package com.shinhan.maahproject.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.MyCardHiDTO;
import com.shinhan.maahproject.dto.VirtualCardInfoDTO;
import com.shinhan.maahproject.service.MyCardListService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MyCardListRestController {

	@Autowired
	MyCardListService mclService;
	
	@PostMapping(value = "/getCardList.do", consumes = "application/json")
	public MyCardHiDTO getVirtualCard(@RequestBody MemberDTO member) {
		
		return mclService.getHiCardInfo(member.getMemberId());
	}
}
