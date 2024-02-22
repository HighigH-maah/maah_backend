package com.shinhan.maahproject.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.BenefitDTO;
import com.shinhan.maahproject.dto.ByCardDTO;
import com.shinhan.maahproject.dto.MemberBenefitDTO;
import com.shinhan.maahproject.dto.MemberCardByDTO;
import com.shinhan.maahproject.dto.MemberCardHiShareDTO;
import com.shinhan.maahproject.dto.ShareByPointUpdateDTO;
import com.shinhan.maahproject.repository.BenefitCategoryRepository;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.repository.ByRelationBenefitRepository;
import com.shinhan.maahproject.repository.MemberBenefitRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.PointByRepository;
import com.shinhan.maahproject.vo.BenefitCategoryVO;
import com.shinhan.maahproject.vo.ByCardVO;
import com.shinhan.maahproject.vo.ByRelationBenefitVO;
import com.shinhan.maahproject.vo.MemberBenefitVO;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;
import com.shinhan.maahproject.vo.PointByVO;

import jakarta.transaction.Transactional;
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

	@Autowired
	MemberBenefitRepository mbhRepo;

	@Autowired
	PointByRepository pbRepo;

	public Object getMemberHiCard(String memberId) {
		MemberVO member = mRepo.findById(memberId).orElse(null);

		ModelMapper mapper = new ModelMapper();

//		log.info(member.toString());
		MemberCardHiVO hicard = mhRepo.findFirstMemberCardHi(member);

		List<MemberBenefitDTO> mbhList = new ArrayList<>();

		List<MemberBenefitVO> mbhvoList = mbhRepo.findByMemberBenefitMemberIdMemberId(memberId);
		for (MemberBenefitVO mbh : mbhvoList) {
			MemberBenefitDTO mbhdto = mapper.map(mbh, MemberBenefitDTO.class);
			mbhList.add(mbhdto);
		}

		log.info(hicard.toString());
		MemberCardHiShareDTO hiShare = MemberCardHiShareDTO.builder().memberHiNickname(hicard.getMemberHiNickname())
				.memberHiPoint(hicard.getMemberHiPoint()).hiImageCode(hicard.getHiImageCode())
				.memberBenefitList(mbhList).memberHiNumber(hicard.getMemberHiNumber()).build();

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

			// 바이카드 DTO 생성 혜택 리스트 뽑기
			List<BenefitDTO> benefitList = new ArrayList<>();

			for (ByRelationBenefitVO rb : rblist) {
				BenefitDTO bDto = BenefitDTO.builder().byBenefitDesc(rb.getBenefits().getByBenefitDesc())
						.benefitCode(rb.getBenefits().getByBenefitCategory().getBenefitCode())
						.byBenefitMinCondition(rb.getBenefits().getByBenefitMinCondition()).build();
				benefitList.add(bDto);
			}
			Collections.sort(benefitList, Comparator.comparingInt(BenefitDTO::getByBenefitMinCondition));

			byCardDto.setBenefitList(benefitList);
			byCardDto.setByCategoryList(bydto.getByCard().getByCategoryList());

			// 멤버 바이 DTO에 넣기
			bydto.setByCard(byCardDto);

			// 이번 달 찾기
			String currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
			List<PointByVO> pointList = pbRepo.findByMemberByNumberAndPointByMonth(bycard, currentYearMonth);
			int pointByAmount = 0;
			if (!pointList.isEmpty()) {
				pointByAmount = pointList.get(0).getPointByAmount();
			} else {
				PointByVO point = PointByVO.builder().memberByNumber(bycard).pointByMonth(currentYearMonth)
						.pointByAmount(0).build();
				pbRepo.save(point);
			}
			// 이번 달 바이포인트 넣기
			bydto.setPointByAmount(pointByAmount);

			// 멤버 바이 DTO List에 넣기
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

	@Transactional
	public ShareByPointUpdateDTO updateByPoint(ShareByPointUpdateDTO sharePointInput) {
		MemberVO member = mRepo.findById(sharePointInput.getMemberId()).orElse(null);
		MemberCardByVO memByCard = mbRepo.findById(sharePointInput.getByCardNumber()).orElse(null);
		String currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));

		MemberCardHiVO memHiCard = mhRepo.findFirstMemberCardHi(member);

		// 존재하는 포인트보다 조작하고 싶은게 클 경우
		if (memHiCard.getMemberHiPoint() < sharePointInput.getAmount()) {
			return null;
		}

		List<PointByVO> pointList = pbRepo.findByMemberByNumberAndPointByMonth(memByCard, currentYearMonth);
		PointByVO point = null;
		if (pointList.isEmpty()) {
			point = PointByVO.builder().memberByNumber(memByCard).pointByMonth(currentYearMonth)
					.pointByAmount(sharePointInput.getAmount()).build();
			pbRepo.save(point);
			;
		} else {
			// 이번 달 포인트가 있는 경우
			point = pointList.get(0);
			point.setPointByAmount(point.getPointByAmount() + sharePointInput.getAmount());
		}
		memHiCard.setMemberHiPoint(memHiCard.getMemberHiPoint() - sharePointInput.getAmount());

		sharePointInput.setHiAmount(memHiCard.getMemberHiPoint());
		sharePointInput.setByAmount(point.getPointByAmount());

		return sharePointInput;
	}

	@Transactional
	public ShareByPointUpdateDTO returnByPoint(ShareByPointUpdateDTO sharePointInput) {
		MemberCardByVO memByCard = mbRepo.findById(sharePointInput.getByCardNumber()).orElse(null);
		String currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
		List<PointByVO> pointList = pbRepo.findByMemberByNumberAndPointByMonth(memByCard, currentYearMonth);
		// 기존 포인트 정보가 없을 경우 - 뺄 것도 없음.
		if (pointList.isEmpty()) {
			return null;
		}
		PointByVO point = pointList.get(0);

		// 존재하는 포인트보다 조작하고 싶은게 클 경우
		if (point.getPointByAmount() < sharePointInput.getAmount()) {
			return null;
		}

		//바꿀 수 있는 경우
		MemberVO member = mRepo.findById(sharePointInput.getMemberId()).orElse(null);
		MemberCardHiVO memHiCard = mhRepo.findFirstMemberCardHi(member);
		
		//bypoint에서 빼고, hipoint에 더하기
		point.setPointByAmount(point.getPointByAmount() - sharePointInput.getAmount());
		memHiCard.setMemberHiPoint(memHiCard.getMemberHiPoint() + sharePointInput.getAmount());

		sharePointInput.setHiAmount(memHiCard.getMemberHiPoint());
		sharePointInput.setByAmount(point.getPointByAmount());

		return sharePointInput;
	}

}
