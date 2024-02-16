package com.shinhan.maahproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.ByCardDTO;
import com.shinhan.maahproject.dto.MemberCardByDTO;
import com.shinhan.maahproject.dto.MemberCardHiShareDTO;
import com.shinhan.maahproject.repository.BenefitCategoryRepository;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.repository.ByRelationBenefitRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.vo.BenefitCategoryVO;
import com.shinhan.maahproject.vo.ByCardVO;
import com.shinhan.maahproject.vo.ByRelationBenefitVO;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShareService {

	@Autowired
	MemberRepository mRepo;

	@Autowired
	MemberCardHiRepository mhRepo;

	@Autowired
	MemberCardByRepository mbRepo;

	@Autowired
	ByCardRepository bRepo;

	@Autowired
	ByRelationBenefitRepository brbRepo;

	@Autowired
	BenefitCategoryRepository bcRepo;

	public Object getMemberHiCard(String memberId) {
		MemberVO member = mRepo.findById(memberId).orElse(null);

		ModelMapper mapper = new ModelMapper();

//		log.info(member.toString());
		MemberCardHiVO hicard = mhRepo.findFirstMemberCardHi(member);
		log.info(hicard.toString());
		MemberCardHiShareDTO hiShare = MemberCardHiShareDTO.builder().memberHiNickname(hicard.getMemberHiNickname())
				.memberHiPoint(hicard.getMemberHiPoint()).hiImageCode(hicard.getHiImageCode()).build();

		MemberCardByVO bycard = getMemberByCard(hicard);
//		MemberCardByDTO bydto = MemberCardByDTO.
		
		MemberCardByDTO bydto = mapper.map(bycard, MemberCardByDTO.class);
//		List<ByRelationBenefitVO> benefits = bycard.getByCard().getBenefits();
//		log.info(benefits.toString());
		HashMap<String, Object> hiAndBy = new HashMap<>();
		hiAndBy.put("hicard", hiShare);
		hiAndBy.put("bycard", bydto);
		return hiAndBy;
	}

	public MemberCardByVO getMemberByCard(MemberCardHiVO hicard) {
		ModelMapper mapper = new ModelMapper();
		List<ByCardDTO> ByCardList = new ArrayList<>();
		
		//JPA 설정. hicard를 가져올 떄 Fetch LAZY가 KEY가 아닌 곳과 연결되어있으면 작동하지 않음
		//그렇기 때문에 hicard에서 cardhistory를 접근하는데, 이 경우 bycard를 새로 조회할 때 같은 이유로 cardhistory가 불러와지고,
		//cardHistory는 hi, by에 모두 연결되어있기 때문에 무한참조 발생
//		hicard.setCardHis(null);
		List<MemberCardByVO> memberByList = mbRepo.findByConnectHiCard(hicard);
		
		
//	    for (ByCardVO byCardVO : byCardVOIterable) {
//	        List<ByRelationBenefitVO> brbMultiList = brbRepo.findByCards(byCardVO);
//	        List<String> categoryToDesc = getCategoryDescriptions(byCardVO.getByCategoryList());
//
//	        List<String> blist = new ArrayList<>(); // 혜택 문장들을 모두 담는다
//
//	        for (ByRelationBenefitVO brbMulti : brbMultiList) {
//	            blist.add(brbMulti.getBenefits().getByBenefitDesc());
//	        }
//
//	        ByCardDTO bDto = mapper.map(byCardVO, ByCardDTO.class);
//	        bDto.setBenefitList(blist); // 혜택을 다음과 같이 세팅
//	        bDto.setByCategoryList(categoryToDesc);
//
//	        ByCardList.add(bDto);
//	        // log.info(ByCardList.toString());
//	    }
		return memberByList.get(0);
	}

	// 카테고리 리스트를 설명으로 바꿔주는 함수
	private List<String> getCategoryDescriptions(String clist) {
		List<String> categoryToDesc = new ArrayList<>();

		if (clist != null) {
			String[] categoryDescList = clist.split(", ");
			for (String number : categoryDescList) {
				try {
					int num = Integer.parseInt(number.trim());
					BenefitCategoryVO bc = bcRepo.findById(num).orElse(null);
					if (bc != null) {
						categoryToDesc.add(bc.getBenefitName().toString());
					} else {
						// log.warn("Benefit category not found for ID: " + num);
					}
				} catch (NumberFormatException e) {
					// log.error("Invalid number format: " + number, e);
				}
			}
		}

		return categoryToDesc;
	}

}
