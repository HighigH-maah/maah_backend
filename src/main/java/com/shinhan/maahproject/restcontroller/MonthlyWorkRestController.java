package com.shinhan.maahproject.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.service.MonthlyService;

@RestController
public class MonthlyWorkRestController {

	@Autowired
	MonthlyService mtService;
	
	@GetMapping("/getMonthRemainTest.do")
	public void getMonthRemainTest() {
		mtService.remainPointShare("user3");
	}
	
	@GetMapping("/getMemberBenefitChange.do")
	public void getMemberBenefitChange() {
		mtService.memberBenefitChange("user4");
	}
}
