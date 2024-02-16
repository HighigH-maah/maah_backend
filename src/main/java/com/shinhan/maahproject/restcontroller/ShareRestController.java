package com.shinhan.maahproject.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.MemberCardHiShareDTO;
import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.service.ByCardService;
import com.shinhan.maahproject.service.ShareService;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ShareRestController {
	
	@Autowired
	ShareService sService;
	
	
	@PostMapping(value="getmemberHiCard.do")
	public Object getMemberHiCard(@RequestBody MemberDTO memberInput) {
		MemberCardHiShareDTO mhis = null;		

		return sService.getMemberHiCard(memberInput.getMemberId());
	}
}
