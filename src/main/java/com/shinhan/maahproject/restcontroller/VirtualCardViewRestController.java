package com.shinhan.maahproject.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.VirtualCardViewDTO;
import com.shinhan.maahproject.service.VirtualCardViewService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class VirtualCardViewRestController {

	@Autowired
	VirtualCardViewService vcService;
	
	//@RequestBody String member_id
	@GetMapping(value="/virtualCardView.do", consumes = "application/json")
	public VirtualCardViewDTO getMember() {
		String member_id = "user3";
		VirtualCardViewDTO vcDto = vcService.getVirtualCardInfo(member_id);
		log.info(vcDto.toString());

		return vcDto;
	}
}
