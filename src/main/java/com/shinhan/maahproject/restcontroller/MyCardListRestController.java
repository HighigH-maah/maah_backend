package com.shinhan.maahproject.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.AccountCheckDTO;
import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.MyCardByDTO;
import com.shinhan.maahproject.dto.MyCardHiDTO;
import com.shinhan.maahproject.service.MyCardListService;
import com.shinhan.maahproject.vo.BankVO;
import com.shinhan.maahproject.vo.MemberAccountMultikey;
import com.shinhan.maahproject.vo.MemberAccountVO;
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
	
	@PutMapping(value = "/updateHiAccount.do", consumes = "application/json")
	public int updateHiAccount(@RequestBody AccountCheckDTO accch) {
		//System.out.println(accch);
		
//		BankVO bank = BankVO.builder()
//				.bankCode(accch.getBankCode())
//				.bankName("aa")
//				.build();
//		MemberAccountMultikey key = MemberAccountMultikey.builder()
//				.memberAccountNumber(accch.getBankName())
//				.bank(bank)
//				.build();
//		MemberAccountVO account = MemberAccountVO.builder()
//				.memberAccountKey(key)
//				.build();
//		
//		mchRefo.findById(accch.getCardNumber()).ifPresent(hi->{
//			System.out.println(hi.getMemberAccountKey());
//			hi.setMemberAccountKey(account);
//			mchRefo.save(hi);
//		});
		
//		MemberCardHiVO hi = MemberCardHiVO.builder()
//				.memberAccountKey(account)
//				.memberHiStatus(0)
//				.memberHiCvc("")
//				.memberHiPassword("")
//				.build();
		
		return mclService.updateHiAccount(accch);
		
	}
	
}
