package com.shinhan.maahproject.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.BankDTO;
import com.shinhan.maahproject.dto.ByCardDTO;
import com.shinhan.maahproject.dto.OtherCardDTO;
import com.shinhan.maahproject.dto.OtherCardInputDTO;
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
//	
//	@PostMapping(value="/compare.do",consumes = "application/json")
//	public OtherCardDTO getOtherCard(@RequestBody OtherCardDTO otherCode) {
//		OtherCardDTO other = oService.getOtherCard(otherCode.getOtherCode());
//		log.info(other.toString());
//		return other;
//	}
//	
	@GetMapping(value="/allbycards.do")
	public List<ByCardDTO> getAllByCard(){
		return  bService.getAllByCard();
	}
	
	@PostMapping(value="/selectByCondition.do", consumes = "application/json")
	public String selectByCondition(
			@RequestBody OtherCardInputDTO otherInput) {
		log.info("냐옹 "+otherInput.toString());
		String bname = otherInput.getBankName();
	
	    
	    return bname;
	}

}
