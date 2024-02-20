package com.shinhan.maahproject.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.AccountCheckDTO;
import com.shinhan.maahproject.dto.LostCardChooseListDTO;
import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.MyCardByDTO;
import com.shinhan.maahproject.dto.MyCardHiDTO;
import com.shinhan.maahproject.dto.MyCardNotByDTO;
import com.shinhan.maahproject.service.MyCardListService;
import com.shinhan.maahproject.vo.BankVO;
import com.shinhan.maahproject.vo.MemberAccountMultikey;
import com.shinhan.maahproject.vo.MemberAccountVO;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;

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
	
	@PostMapping(value = "/getMyCardListNotBy.do", consumes = "application/json")
	public List<MyCardNotByDTO> getMyCardListNotBy(@RequestBody MemberDTO member) {
		return mclService.getMyCardListNotBy(member.getMemberId());
	}
	
	@PutMapping(value = "/updateHiAccount.do", consumes = "application/json")
	public int updateHiAccount(@RequestBody AccountCheckDTO accch) {
		
		return mclService.updateHiAccount(accch);
	}
	
	@PutMapping(value = "/updateByAccount.do", consumes = "application/json")
	public int updateByAccount(@RequestBody AccountCheckDTO accch) {
		
		return mclService.updateByAccount(accch);
	}
	
	@PostMapping(value = "/excludeHiCard.do", consumes = "application/json")
	public int excludeHiCard(@RequestBody MyCardByDTO myCardBy) {
		return mclService.excludeHiCard(myCardBy);
	}
	
	@PostMapping(value = "/addHiCard.do", consumes = "application/json")
	public int addHiCard(@RequestBody MyCardNotByDTO myCardNotBy) {
		
		return mclService.addHiCard(myCardNotBy);
	}
	
	@PostMapping(value = "/getlostCardChooseList.do", consumes = "application/json")
	public List<LostCardChooseListDTO> getlostCardChooseList(@RequestBody MemberDTO member){
		
		return mclService.getlostCardChooseList(member.getMemberId());
	}
	
	@PostMapping(value = "/reportLost.do", consumes = "application/json")
	public int reportLost(@RequestBody LostCardChooseListDTO lostCard) {
		
		return mclService.reportLost(lostCard.getMemberCardNumber());
	}
	
}
