package com.shinhan.maahproject.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.ByCardDTO;
import com.shinhan.maahproject.dto.OtherCardDTO;
import com.shinhan.maahproject.service.ByCardService;
import com.shinhan.maahproject.service.OtherCardService;

import lombok.extern.slf4j.Slf4j;

//@CrossOrigin
@RestController
@Slf4j
public class CardCompareRestController {
	@Autowired
	OtherCardService oService;
	
	@Autowired
	ByCardService bService;
	
	@PostMapping(value="/compare.do",consumes = "application/json")
	public OtherCardDTO getOtherCard(@RequestBody OtherCardDTO other_code) {
		OtherCardDTO other = oService.getOtherCard(other_code.getOther_code());
		log.info(other.toString());
		return other;
	}
	
	@PostMapping(value="/allbycards.do",consumes = "application/json")
	public List<ByCardDTO> getAllByCard(){
		return  bService.getAllByCard();
	}

}
