package com.shinhan.maahproject.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.maahproject.dto.BannerLinkDTO;
import com.shinhan.maahproject.dto.HiCardDetailDTO;
import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.MyCardHiDTO;
import com.shinhan.maahproject.dto.MyDataDTO;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.service.HICardDetailService;
import com.shinhan.maahproject.vo.HiCardImageVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MainRestController {

	@Autowired
	MemberRepository mRepo;

	@Autowired
	HICardDetailService hdService;

	@Autowired
	MemberCardHiRepository mhRepo;

	@PostMapping(value="/getMemberInfo.do", consumes = "application/json")
	public BannerLinkDTO getMemberInfo(@RequestBody MemberDTO memberId) {
		String memId = memberId.getMemberId();
		MyDataDTO myData = new MyDataDTO();
		HiCardDetailDTO hiCardInfo = hdService.getHiCardInfo(memId); // 멤버의 하이카드 정보
		
		BannerLinkDTO bannerInfo = new BannerLinkDTO();
		bannerInfo.setMemberId(memId);
		bannerInfo.setMyHiNumber(hiCardInfo.getMemberHiNumber());
		bannerInfo.setHiCardImageCode(0);
		bannerInfo.setHiCardImageName(memId);
		List<MemberCardHiVO> list = mhRepo.findByMemberHiNumber(hiCardInfo.getMemberHiNumber());
		
		MemberCardHiVO mem = list.get(0);
		HiCardImageVO image = mem.getHiImageCode();
		bannerInfo.setHiCardImageCode(image.getHiCardImageCode());
		bannerInfo.setHiCardImageName(image.getHiCardImageName());
		return bannerInfo;
		
	
		
		
		
	}

}
