package com.shinhan.maahproject.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.service.CardApplicationService;
import com.shinhan.maahproject.vo.HiCardImageVO;

@RestController
public class CardApplicationController {

	@Autowired
	CardApplicationService service;
	
	@PostMapping("/getHiCardImages")
	public List<HiCardImageVO> getHicardDesigns(@RequestBody Map<String, String> data) {
		return service.getHicardDesigns(data.get("member_id").toString());
	}
}
