package com.shinhan.maahproject.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.ByCardDetailDTO;
import com.shinhan.maahproject.dto.HiCardDetailDTO;
import com.shinhan.maahproject.service.ByCardDetailService;
import com.shinhan.maahproject.service.CardHistoryService;
import com.shinhan.maahproject.service.HICardDetailService;
import com.shinhan.maahproject.vo.CardHistoryVO;
import com.shinhan.maahproject.vo.MemberAccountVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MyDataRestController {
	@Autowired
	HICardDetailService hdService;

	@Autowired
	CardHistoryService chService;

	@Autowired
	ByCardDetailService byService;

	@GetMapping(value = "/getMyLimit.do")
	public Long getMyLimit() {
		HiCardDetailDTO hiCardInfo = hdService.getHiCardInfo("user2"); // 멤버의 하이카드 정보
		String HiNumber = hiCardInfo.getMemberHiNumber(); // 해당 유저의 하이카드 번호

		return chService.getHistory(HiNumber);
	}

	@GetMapping(value = "/getTest.do")
	public List<MemberAccountVO> getTest() {
		return chService.getAccount();
	}
}
