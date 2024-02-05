package com.shinhan.maahproject.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.service.MemberService;

import lombok.extern.slf4j.Slf4j;

//@CrossOrigin
@RestController
@Slf4j
public class MemberRestController {
	@Autowired
	MemberService mService;
	
	@PostMapping(value="/member.do", consumes = "application/json")
	public MemberDTO getMember(@RequestBody MemberDTO member_id) {
		
		MemberDTO member = mService.getMember(member_id.getMember_id());
		log.info(member.toString());

		return member;
	}
	
	@GetMapping(value="/member.do/{member_id}")
	public MemberDTO getMember2(@PathVariable String member_id) {
		
		MemberDTO member = mService.getMember(member_id);
		log.info(member.toString());

		return member;
	}
}
