package com.shinhan.maahproject.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.AccountCheckDTO;
import com.shinhan.maahproject.dto.BenefitDTO;
import com.shinhan.maahproject.dto.ByCardDTO;
import com.shinhan.maahproject.dto.LostCardChooseListDTO;
import com.shinhan.maahproject.dto.MemberCardByDTO;
import com.shinhan.maahproject.dto.MyCardByDTO;
import com.shinhan.maahproject.dto.MyCardHiDTO;
import com.shinhan.maahproject.dto.MyCardNotByDTO;
import com.shinhan.maahproject.repository.BankRepository;
import com.shinhan.maahproject.repository.ByRelationBenefitRepository;
import com.shinhan.maahproject.repository.CardHistoryRepository;
import com.shinhan.maahproject.repository.MemberAccountRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.PointByRepository;
import com.shinhan.maahproject.vo.BankVO;
import com.shinhan.maahproject.vo.ByBenefitVO;
import com.shinhan.maahproject.vo.ByRelationBenefitVO;
import com.shinhan.maahproject.vo.CardHistoryVO;
import com.shinhan.maahproject.vo.ClassBenefitVO;
import com.shinhan.maahproject.vo.MemberAccountMultikey;
import com.shinhan.maahproject.vo.MemberAccountVO;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;
import com.shinhan.maahproject.vo.PointByMultikey;
import com.shinhan.maahproject.vo.PointByVO;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MyCardListService {

	@Autowired
	MemberRepository mRepo;

	@Autowired
	MemberCardHiRepository mchRepo;

	@Autowired
	MemberCardByRepository mcbRepo;

	@Autowired
	MemberAccountRepository maRepo;

	@Autowired
	BankRepository bankRepo;

	@Autowired
	PointByRepository pbRepo;

	@Autowired
	CardHistoryRepository chRepo;
	
	@Autowired
	ByRelationBenefitRepository bbRepo;

	// 나의 하이카드
	public MyCardHiDTO getMyCardListHi(String memberId) {

		ModelMapper mapper = new ModelMapper();

		MemberVO member = mRepo.findById(memberId).orElse(null);

		List<MemberCardHiVO> mhicards = mchRepo.findByMemberHiOwnerWithHiImageCode(member);

		ClassBenefitVO cb = member.getClassBenefit();
		// log.info(cb.toString());

		MemberCardHiVO mhicard = mhicards.get(0);

		// 현재 날짜와 비교할 YearMonth 객체 생성
		YearMonth currentYearMonth = YearMonth.now();

		// 이번 달의 시작일과 마지막일 계산
		LocalDate firstDayOfMonth = currentYearMonth.atDay(1);
		LocalDate lastDayOfMonth = currentYearMonth.atEndOfMonth();

		// 조건을 추가하여 이번 달의 cardHistoryAmount 합계 계산
		int thisMonthSum = chRepo.findByMemberCardHi(mhicard).stream().filter(ch -> {
			// cardHistoryDate가 이번 달에 속하는지 확인
			LocalDate date = ch.getCardHistoryDate().toLocalDateTime().toLocalDate();
			return date.isEqual(firstDayOfMonth) || (date.isAfter(firstDayOfMonth) && date.isBefore(lastDayOfMonth));
		}).mapToInt(CardHistoryVO::getCardHistoryAmount).sum();

		Integer totalLimit = mchRepo.sumHiCardTotalLimitByMemberBYOwner(member, mhicard);

		MyCardHiDTO resultMyHiCard = mapper.map(mhicard, MyCardHiDTO.class);
		resultMyHiCard.setThisMonthSum(thisMonthSum);
		resultMyHiCard.setTotalLimit(totalLimit);
		resultMyHiCard.setClassBenefitName(cb.getClassBenefitName());

		// log.info(resultMyHiCard.toString());

		return resultMyHiCard;

	}

	public Map<String, Timestamp> getThisMonth() {

		Map<String, Timestamp> thisMonth = new HashMap<>();

		LocalDateTime now = LocalDateTime.now();
		YearMonth ym = YearMonth.from(now);

		LocalDate startDate = ym.atDay(1);
		LocalDate endDate = ym.atEndOfMonth();

		LocalTime startTime = LocalTime.MIN;
		LocalTime endTime = LocalTime.MAX;

		thisMonth.put("startTimestamp", Timestamp.valueOf(LocalDateTime.of(startDate, startTime)));
		thisMonth.put("endTimestamp", Timestamp.valueOf(LocalDateTime.of(endDate, endTime)));

		return thisMonth;
	}

	// 나의 바이카드 리스트
	public List<MyCardByDTO> getMyCardListBy(String memberId) {
		
		ModelMapper mapper = new ModelMapper();
		MemberVO member = mRepo.findById(memberId).orElse(null);
		String pointByMonth = null;
		int pointByAmount = 0;
		// 현재 년월 가져오기
		String currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));

		// List<ByCardDTO> ByCardList = new ArrayList<>();

		List<MemberCardByVO> mbycards = (List<MemberCardByVO>) mcbRepo
				.findByMemberAndMemberByStatusAndConnectHiCardNotNullOrderByMemberByRank(member, 0);

		List<MyCardByDTO> myCardListBy = new ArrayList<>();

		// 바이카드 리스트에서 바이DTO 리스트로 변경
		for (MemberCardByVO bycard : mbycards) {
			MyCardByDTO bydto = mapper.map(bycard, MyCardByDTO.class);
			ByCardDTO byCardDto = bydto.getByCard();
			List<ByRelationBenefitVO> rblist = bycard.getByCard().getBenefits();

			// 바이카드 DTO 생성 혜택 리스트 뽑기
			List<BenefitDTO> benefitList = new ArrayList<>();

			for (ByRelationBenefitVO rb : rblist) {
				BenefitDTO bDto = BenefitDTO.builder().byBenefitDesc(rb.getBenefits().getByBenefitDesc())
						.benefitCode(rb.getBenefits().getByBenefitCategory().getBenefitCode()).build();
				benefitList.add(bDto);
			}
			byCardDto.setBenefitList(benefitList);
			byCardDto.setByCategoryList(bydto.getByCard().getByCategoryList());

			// 멤버 바이 DTO에 넣기
			bydto.setByCard(byCardDto);
			// bydto.setByBenefitMinCondition(byCardDto.getByMinLimit());

			for (PointByVO pbvo : pbRepo.findByMemberByNumberAndPointByMonth(bycard, currentYearMonth)) {
				pointByMonth = pbvo.getPointByMonth(); // pointByMonth
				pointByAmount = pbvo.getPointByAmount(); // pointByAmount
			}

			// ByBenefitMinCondition의 최솟값 구하기
			List<ByRelationBenefitVO> relationBenefitList = bbRepo.findByCards(bycard.getByCard());
			int minByBenefitMinCondition = Integer.MAX_VALUE;
			for (ByRelationBenefitVO bbvo : relationBenefitList) {
				ByBenefitVO bybenefits = bbvo.getBenefits();
				int byBenefitMinCondition = bybenefits.getByBenefitMinCondition();
				minByBenefitMinCondition = Math.min(minByBenefitMinCondition, byBenefitMinCondition);
			}
			int byBenefitMinCondition = minByBenefitMinCondition; // 최솟값 설정

			bydto.setByBenefitMinCondition(byBenefitMinCondition);
			bydto.setPointByAmount(pointByAmount);
			;
			// 멤버 바이 DTO List에 넣기
			myCardListBy.add(bydto);

		}

//		List<MyCardByDTO> myCardListBy = mbycards.stream()
//				.map(memberCardByVO -> mapper.map(memberCardByVO, MyCardByDTO.class)).collect(Collectors.toList());

		//System.out.println("77777777777"+myCardListBy);
		return myCardListBy;
	}

	// 나의 낫바이카드 리스트
	public List<MyCardNotByDTO> getMyCardListNotBy(String memberId) {
		ModelMapper mapper = new ModelMapper();
		MemberVO member = mRepo.findById(memberId).orElse(null);
		String pointByMonth = null;
		int pointByAmount = 0;
		// 현재 년월 가져오기
		String currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));

		List<MemberCardByVO> mNotbycards = (List<MemberCardByVO>) mcbRepo
				.findByMemberAndMemberByStatusAndConnectHiCardNullOrderByMemberByRank(member, 0);


//		List<MyCardNotByDTO> myCardListNotBy = mNotbycards.stream().map(memberCardByVO -> {
//			MyCardNotByDTO dto = mapper.map(memberCardByVO, MyCardNotByDTO.class);
//
//			// 시작일과 종료일 설정
//			LocalDate currentDate = LocalDate.now();
//			Timestamp startDate = Timestamp.valueOf(currentDate.withDayOfMonth(1).atStartOfDay());
//			Timestamp endDate = Timestamp.valueOf(currentDate
//					.withDayOfMonth(currentDate.getMonth().length(currentDate.isLeapYear())).atTime(23, 59, 59));
//
//			Integer thisMonthSum = chRepo.findByMemberCardBy(startDate, endDate, memberCardByVO);
//
//			int sum = (thisMonthSum != null) ? thisMonthSum : 0; // null 체크하여 기본값 할당
//
//			dto.setThisMonthSum(sum);
//
//			return dto;
//		}).collect(Collectors.toList());
		
		List<MyCardNotByDTO> myCardListNotBy = new ArrayList<>();

		// 바이카드 리스트에서 바이DTO 리스트로 변경
		for (MemberCardByVO bycard : mNotbycards) {
			MyCardNotByDTO bydto = mapper.map(bycard, MyCardNotByDTO.class);
			ByCardDTO byCardDto = bydto.getByCard();
			List<ByRelationBenefitVO> rblist = bycard.getByCard().getBenefits();

			// 바이카드 DTO 생성 혜택 리스트 뽑기
			List<BenefitDTO> benefitList = new ArrayList<>();

			for (ByRelationBenefitVO rb : rblist) {
				BenefitDTO bDto = BenefitDTO.builder().byBenefitDesc(rb.getBenefits().getByBenefitDesc())
						.benefitCode(rb.getBenefits().getByBenefitCategory().getBenefitCode()).build();
				benefitList.add(bDto);
			}
			byCardDto.setBenefitList(benefitList);
			byCardDto.setByCategoryList(bydto.getByCard().getByCategoryList());

			// 멤버 바이 DTO에 넣기
			bydto.setByCard(byCardDto);
			// bydto.setByBenefitMinCondition(byCardDto.getByMinLimit());

			for (PointByVO pbvo : pbRepo.findByMemberByNumberAndPointByMonth(bycard, currentYearMonth)) {
				pointByMonth = pbvo.getPointByMonth(); // pointByMonth
				pointByAmount = pbvo.getPointByAmount(); // pointByAmount
			}

			// ByBenefitMinCondition의 최솟값 구하기
			List<ByRelationBenefitVO> relationBenefitList = bbRepo.findByCards(bycard.getByCard());
			int minByBenefitMinCondition = Integer.MAX_VALUE;
			for (ByRelationBenefitVO bbvo : relationBenefitList) {
				ByBenefitVO bybenefits = bbvo.getBenefits();
				int byBenefitMinCondition = bybenefits.getByBenefitMinCondition();
				minByBenefitMinCondition = Math.min(minByBenefitMinCondition, byBenefitMinCondition);
			}
			int byBenefitMinCondition = minByBenefitMinCondition; // 최솟값 설정

			bydto.setByBenefitMinCondition(byBenefitMinCondition);
			
			LocalDate currentDate = LocalDate.now();
			Timestamp startDate = Timestamp.valueOf(currentDate.withDayOfMonth(1).atStartOfDay());
			Timestamp endDate = Timestamp.valueOf(currentDate
					.withDayOfMonth(currentDate.getMonth().length(currentDate.isLeapYear())).atTime(23, 59, 59));

			Integer thisMonthSum = chRepo.findByMemberCardBy(startDate, endDate, bycard);

			int sum = (thisMonthSum != null) ? thisMonthSum : 0; // null 체크하여 기본값 할당

			bydto.setThisMonthSum(sum);
			// 멤버 바이 DTO List에 넣기
			myCardListNotBy.add(bydto);

		}
		
		//System.out.println("8888888888888"+myCardListNotBy);
		return myCardListNotBy;
	}

	// 하이카드 계좌변경
	@Transactional
	public int updateHiAccount(AccountCheckDTO accch) {

		int result = 0;
		MemberVO member = mRepo.findById(accch.getMemberId()).orElse(null);

		log.info(accch.toString());
		List<MemberCardHiVO> mhicards = mchRepo
				.findByMemberHiOwnerAndMemberHiStatus(mRepo.findById(accch.getMemberId()).orElse(null), 0);

		List<MemberAccountVO> mAccounts = maRepo.findByMemberAccountNumberAndBankBankCode(accch.getBankName(),
				accch.getBankCode());
		log.info(mAccounts.toString());
		// 신규 등록 필요x
		if (mAccounts.size() != 0) {
			MemberAccountVO mAcc = mAccounts.get(0);
			log.info(mAcc.toString());
			for (MemberCardHiVO mc : mhicards) {
				mc.setMemberAccountKey(mAcc);
				log.info("dddd" + mc.toString());
			}
		}
		// 신규 등록 필요
		else {
			log.info("신규 등록 필요");

			BankVO bank = bankRepo.findById(accch.getBankCode()).orElse(null);

			assert bank != null;

			System.out.println("aaaaa" + bank);

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			MemberAccountVO account = MemberAccountVO.builder().memberAccountNumber(accch.getBankName()).bank(bank)
					.memberAccountMemberId(member).memberAccountBalance(0).memberAccountRegdate(timestamp)
					.memberAccountStatus(0).memberAccountPassword("1234").build();

			if (!bankRepo.existsById(bank.getBankCode())) {
				bankRepo.save(bank);
			}

			MemberAccountVO savedAcc = maRepo.save(account);

			System.out.println("bbbbb" + savedAcc);

			MemberCardHiVO mcHi = mhicards.get(0);
			mcHi.setMemberAccountKey(savedAcc);
			System.out.println("5555555555555555" + mcHi.getMemberAccountKey());

		}

		return result;
	}

	// 바이카드 계좌변경
	public int updateByAccount(AccountCheckDTO accch) {
		
		int result = 0;
		MemberVO member = mRepo.findById(accch.getMemberId()).orElse(null);

		List<MemberCardByVO> mcbList = mcbRepo.findByMemberByNumber(accch.getCardNumber());

		List<MemberAccountVO> mAccounts = maRepo.findByMemberAccountNumberAndBankBankCode(accch.getBankName(),
				accch.getBankCode());

		if (mAccounts.size() != 0) {
			MemberAccountVO mAcc = mAccounts.get(0);
			log.info(mAcc.toString());
			for (MemberCardByVO mcb : mcbList) {
				mcb.setMemberAccountKey(mAcc);
				log.info("dddd" + mcb.toString());
				mcbRepo.save(mcb);
			}
		}
		// 신규 등록 필요
		else {
			log.info("신규 등록 필요");

			BankVO bank = bankRepo.findById(accch.getBankCode()).orElse(null);

			assert bank != null;

			System.out.println("aaaaa" + bank);

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			MemberAccountVO account = MemberAccountVO.builder().memberAccountNumber(accch.getBankName()).bank(bank)
					.memberAccountMemberId(member).memberAccountBalance(0).memberAccountRegdate(timestamp)
					.memberAccountStatus(0).memberAccountPassword("1234").build();

			if (!bankRepo.existsById(bank.getBankCode())) {
				bankRepo.save(bank);
			}

			MemberAccountVO savedAcc = maRepo.save(account);

			System.out.println("bbbbb" + savedAcc);

			MemberCardByVO mcBy = mcbList.get(0);
			mcBy.setMemberAccountKey(savedAcc);
			System.out.println("5555555555555555" + mcBy.getMemberAccountKey());
			
			mcbRepo.save(mcBy);

		}

		return result;
	}

	// 바이카드-하이카드제외
	public int excludeHiCard(MyCardByDTO myCardBy) {

		List<MemberCardByVO> mcbList = mcbRepo.findByMemberByNumber(myCardBy.getMemberByNumber());

		MemberCardByVO memByCard = mcbList.get(0);

		// System.out.println("aaaaaaaaaaaaaaaaa"+memByCard.getConnectHiCard().getMemberHiNumber());

		memByCard.setConnectHiCard(null);

		mcbRepo.save(memByCard);

		return 0;
	}

	// 바이카드-하이카드연결
	public int addHiCard(MyCardNotByDTO myCardNotBy) {

		MemberVO member = mRepo.findById(myCardNotBy.getMemberId()).orElse(null);

		List<MemberCardHiVO> mchList = mchRepo.findByMemberHiOwnerAndMemberHiStatus(member, 0);

		MemberCardHiVO memHiCard = mchList.get(0);

		List<MemberCardByVO> mcbList = mcbRepo.findByMemberByNumber(myCardNotBy.getMemberByNumber());

		MemberCardByVO memByCard = mcbList.get(0);

		memByCard.setConnectHiCard(memHiCard);

		mcbRepo.save(memByCard);

		return 0;
	}

	// 분실카드 선택 리스트
	public List<LostCardChooseListDTO> getlostCardChooseList(String memberId) {
		MemberVO member = mRepo.findById(memberId).orElse(null);

		List<LostCardChooseListDTO> resultList = new ArrayList<>();

		List<MemberCardHiVO> mhicards = mchRepo.findByMemberHiOwnerAndMemberHiStatus(member, 0);

		MemberCardHiVO mhicard = mhicards.get(0);

		LostCardChooseListDTO card = LostCardChooseListDTO.builder().memberCardNumber(mhicard.getMemberHiNumber())
				.memberCardNickname(mhicard.getMemberHiNickname())
				.cardImageFrontPath(mhicard.getHiImageCode().getHiCardImageFrontPath()).isHiCard(true).build();

		resultList.add(card);

		List<MemberCardByVO> mbycards = (List<MemberCardByVO>) mcbRepo.findByMemberAndMemberByStatus(member, 0);

		for (int i = 0; i < mbycards.size(); i++) {
			card = LostCardChooseListDTO.builder().memberCardNumber(mbycards.get(i).getMemberByNumber())
					.memberCardNickname(mbycards.get(i).getMemberCardByNickname())
					.cardImageFrontPath(mbycards.get(i).getByCard().getByImagePath()).isHiCard(false).build();

			resultList.add(card);

		}

		return resultList;
	}

	//분실신고
	public int reportLost(String memberCardNumber) {
		
		List<MemberCardHiVO> mchList = mchRepo.findByMemberHiNumber(memberCardNumber);
		
		if(mchList.size() > 0) {
			MemberCardHiVO mch = mchList.get(0);
			mch.setMemberHiStatus(1);
			mchRepo.save(mch);
		}else {
			List<MemberCardByVO> mcbList = mcbRepo.findByMemberByNumber(memberCardNumber);
			MemberCardByVO mcb = mcbList.get(0);
			mcb.setMemberByStatus(1);
			mcbRepo.save(mcb);
		}

		return 0;
	}

}
