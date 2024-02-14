package com.shinhan.maahproject.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.BankDTO;
import com.shinhan.maahproject.dto.ByCardDetailDTO;
import com.shinhan.maahproject.dto.OtherCardDTO;
import com.shinhan.maahproject.dto.OtherCardInputDTO;
import com.shinhan.maahproject.service.ByCardDetailService;
import com.shinhan.maahproject.service.OtherCardService;
import com.shinhan.maahproject.vo.OtherCardVO;

import lombok.extern.slf4j.Slf4j;

//@CrossOrigin
@RestController
@Slf4j
public class CardCompareRestController {
	@Autowired
	OtherCardService oService;
	
	@Autowired
	ByCardDetailService bService;

	@GetMapping(value="/allbycards.do")
	public List<ByCardDetailDTO> getAllByCard(){
		return  bService.getAllByCard();
	}
	
	@PostMapping(value="/selectByCondition.do", consumes = "application/json")
	public List<OtherCardVO> selectByCondition(
			@RequestBody OtherCardInputDTO otherInput) {
		log.info("냐옹 "+otherInput.toString());
		String bname = otherInput.getBankName();
		int categoryNum = otherInput.getCategory();
	
		List<OtherCardVO> OCards = oService.getOtherCards(bname, categoryNum);
		log.info(OCards.toString());
	    return OCards;
	}
	
	@PostMapping(value="/byCardsByOther.do", consumes = "application/json")
	public List<ByCardDetailDTO> getByCardsByOther(@RequestBody OtherCardDTO otherInput){
		log.info("멍멍"+otherInput.toString());
		return bService.getByCardsByOther(otherInput.getOtherName());
	}

}
