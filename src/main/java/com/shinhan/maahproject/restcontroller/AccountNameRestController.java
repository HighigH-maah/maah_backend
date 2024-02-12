package com.shinhan.maahproject.restcontroller;

import java.io.UnsupportedEncodingException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.AccountCheckDTO;
import com.shinhan.maahproject.utils.PortoneAPI;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AccountNameRestController {
	
	@PostMapping(value = "/getAccountName.do", consumes = "application/json")
	public AccountCheckDTO getAccountName(@RequestBody AccountCheckDTO accoChack) throws UnsupportedEncodingException {
		
		PortoneAPI accountCheck = new PortoneAPI();
		
		String accountNm = accountCheck.getAcountHolderNM(accoChack.getBankCode(),accoChack.getBankName());
		String maberNm = "한마음";
				//accoChack.getMyName();
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
