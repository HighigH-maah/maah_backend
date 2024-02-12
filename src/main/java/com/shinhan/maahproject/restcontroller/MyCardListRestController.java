package com.shinhan.maahproject.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.MyCardByDTO;
import com.shinhan.maahproject.dto.MyCardHiDTO;
import com.shinhan.maahproject.service.MyCardListService;
import com.shinhan.maahproject.vo.MemberCardByVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MyCardListRestController {

	@Autowired
	MyCardListService mclService;
	
	@PostMapping(value = "/getMyCardListHi.do", consumes = "application/json")
	public MyCardHiDTO getMyCardListHi(@RequestBody MemberDTO member) {
		
		return mclService.getMyCardListHi(member.getMemberId());
	}
	
	@PostMapping(value = "/getMyCardListBy.do", consumes = "application/json")
	public List<MyCardByDTO> getMyCardListBy(@RequestBody MemberDTO member) {
		
		return mclService.getMyCardListBy(member.getMemberId());
	}
}
