package com.shinhan.maahproject.restcontroller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.ByCardDetailDTO;
import com.shinhan.maahproject.dto.CategoryBenefitDTO;
import com.shinhan.maahproject.dto.HiCardDetailDTO;
import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.MyDataCompareDTO;
import com.shinhan.maahproject.dto.MyDataDTO;
import com.shinhan.maahproject.dto.MyDataLimitDTO;
import com.shinhan.maahproject.dto.MyNextLevelDTO;
import com.shinhan.maahproject.service.ByCardDetailService;
import com.shinhan.maahproject.service.ByCardService;
import com.shinhan.maahproject.service.CardHistoryService;
import com.shinhan.maahproject.service.HICardDetailService;
import com.shinhan.maahproject.service.MemberService;
import com.shinhan.maahproject.vo.BenefitCategoryVO;
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
	ByCardService byService;

	@Autowired
	MemberService mService;

	@Autowired
	ByCardDetailService bdService;

	@GetMapping(value = "/getMyData.do")
	public MyDataDTO getMyData() {
		MyDataDTO myData = new MyDataDTO();

		HiCardDetailDTO hiCardInfo = hdService.getHiCardInfo("user2"); // 멤버의 하이카드 정보
		String HiNumber = hiCardInfo.getMemberHiNumber(); // 해당 유저의 하이카드 번호
		Map<Integer, List<ByCardDetailDTO>> byCardInfo = bdService.getAllByCardInfo("user2");
		MyDataLimitDTO mylimit = chService.getHistory(HiNumber, byCardInfo);
		myData.setMyLimit(mylimit); // 한도 설정
		myData.setMyNextLevel(chService.tonextLevel("user2")); // 다음 레벨 설정
		myData.setMyCategoryView(chService.getCategoryView(HiNumber, byCardInfo)); // 카테고리 비율 설정
		myData.setMyAvg(chService.getMonthAvg(HiNumber));

		Long diff = 0L;
		diff = Math.abs((chService.getHistory(HiNumber, byCardInfo)).getHistoryAmount()
				- (chService.getLastMonthHistory(HiNumber, byCardInfo)));
		myData.setMyCompare(chService.getCompare(HiNumber, diff));
		return myData;

	}

	@GetMapping(value = "/getTest.do")
	public List<MemberAccountVO> getTest() {
		return chService.getAccount();
	}
}
