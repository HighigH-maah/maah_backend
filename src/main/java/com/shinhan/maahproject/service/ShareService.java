package com.shinhan.maahproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.BenefitDTO;
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

		List<MemberCardByDTO> byCardDtoList = getMemberByCard(hicard);
		
		

		HashMap<String, Object> hiAndBy = new HashMap<>();
		hiAndBy.put("hicard", hiShare);
		hiAndBy.put("bycard", byCardDtoList);
		return hiAndBy;
	}

	public List<MemberCardByDTO> getMemberByCard(MemberCardHiVO hicard) {
		ModelMapper mapper = new ModelMapper();
		List<ByCardDTO> ByCardList = new ArrayList<>();

		// JPA 설정. hicard를 가져올 떄 Fetch LAZY가 KEY가 아닌 곳과 연결되어있으면 작동하지 않음
		// 그렇기 때문에 hicard에서 cardhistory를 접근하는데, 이 경우 bycard를 새로 조회할 때 같은 이유로
		// cardhistory가 불러와지고,
		// cardHistory는 hi, by에 모두 연결되어있기 때문에 무한참조 발생
//		hicard.setCardHis(null);
		List<MemberCardByVO> memberByList = mbRepo.findByConnectHiCard(hicard);

		List<MemberCardByDTO> memberByDtoList = new ArrayList<>();

		// 바이카드 리스트에서 바이DTO 리스트로 변경
		for (MemberCardByVO bycard : memberByList) {
			MemberCardByDTO bydto = mapper.map(bycard, MemberCardByDTO.class);
			ByCardDTO byCardDto = bydto.getByCard();
			List<ByRelationBenefitVO> rblist = bycard.getByCard().getBenefits();

			//바이카드 DTO 생성 혜택 리스트 뽑기
			List<BenefitDTO> benefitList = new ArrayList<>();

			for (ByRelationBenefitVO rb : rblist) {
				BenefitDTO bDto = BenefitDTO.builder()
						.byBenefitDesc(rb.getBenefits().getByBenefitDesc())
						.benefitCode(rb.getBenefits().getByBenefitCategory().getBenefitCode())
						.build();
				benefitList.add(bDto);
			}
			byCardDto.setBenefitList(benefitList);
			byCardDto.setByCategoryList(bydto.getByCard().getByCategoryList());
			
			
			//멤버 바이 DTO에 넣기
			bydto.setByCard(byCardDto);
			
			//멤버 바이 DTO List에 넣기
			memberByDtoList.add(bydto);
		}

		return memberByDtoList;
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
