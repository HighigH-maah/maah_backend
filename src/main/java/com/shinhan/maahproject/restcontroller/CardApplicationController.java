package com.shinhan.maahproject.restcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.CardApplyDTO;
import com.shinhan.maahproject.service.CardApplicationService;

@RestController
public class CardApplicationController {

	@Autowired
	CardApplicationService service;
	
	@PostMapping("/getHiCardImages")
	public Map<String, Object> getHicardDesigns(@RequestBody Map<String, String> data) {	
		return service.getHicardDesigns(data.get("member_id").toString());
	}
	
	@PostMapping(value="/cardApply", consumes = "application/json")
	public String cardApply(@RequestBody CardApplyDTO cardApply) {
		System.out.println(cardApply.toString());
		return "1111";
	}
}
