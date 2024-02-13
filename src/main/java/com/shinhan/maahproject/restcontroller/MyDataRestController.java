package com.shinhan.maahproject.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shinhan.maahproject.dto.HiCardDetailDTO;
import com.shinhan.maahproject.service.CardHistoryService;
import com.shinhan.maahproject.service.HICardDetailService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MyDataRestController {
	@Autowired
	HICardDetailService hdService;
	
	@Autowired
	CardHistoryService chService;
	
	@GetMapping(value="/getMyLimit.do")
	public HiCardDetailDTO getMyLimit(){
		HiCardDetailDTO hiCardInfo = hdService.getHiCardInfo("user3"); // 멤버의 하이카드 정보
		String hiCardName = hiCardInfo.getMemberHiNickname();
		log.info("hiCardName"+hiCardName);
		log.info(chService.getHistory());
		return hiCardInfo;
	}
}
