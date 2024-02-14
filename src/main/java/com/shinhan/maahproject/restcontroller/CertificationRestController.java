package com.shinhan.maahproject.restcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.service.CertificationService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CertificationRestController {

	@Autowired
	CertificationService certService;
	
	@PostMapping(value="/getCert.do", consumes = "application/json")
	public String getCert(@RequestBody Map<String, String> data) {
		//인증성공후 imp_uid안에 있는 이름과, member table에 있는 member_name과 같으면 가상카드번호 발급
		String result = certService.cert(data.get("imp_uid"), data.get("memberId"));
		return result;
	}
}
