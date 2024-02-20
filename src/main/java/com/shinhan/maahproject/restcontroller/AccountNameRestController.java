package com.shinhan.maahproject.restcontroller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.AccountCheckDTO;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.utils.PortoneAPI;
import com.shinhan.maahproject.vo.BankVO;
import com.shinhan.maahproject.vo.MemberAccountMultikey;
import com.shinhan.maahproject.vo.MemberAccountVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AccountNameRestController {
	
	@Autowired
	private PortoneAPI accountCheck;
	
	@PostMapping(value = "/getAccountName.do", consumes = "application/json")
	public AccountCheckDTO getAccountName(@RequestBody AccountCheckDTO accoChack) throws UnsupportedEncodingException {
		
		String accountNm = accountCheck.getAcountHolderNM(accoChack.getBankCode(),accoChack.getBankName());
		String maberNm = accoChack.getMemberName();
				//accoChack.getMemberId();
		accoChack.setAccountChkYn("N");
		
		log.info(accountNm);
		log.info(maberNm);
		
		if(accountNm.equals(maberNm)) {
			accoChack.setAccountChkYn("Y");
			log.info("============= 계좌 예금주명 일치 ============");
		}else {
			log.info("============= 계좌 예금주명 불일치 ==========");
		}
		
		return accoChack;
	}
	

}
