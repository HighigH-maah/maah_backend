package com.shinhan.maahproject.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
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
import com.shinhan.maahproject.dto.LostCardChooseListDTO;
import com.shinhan.maahproject.dto.MyCardByDTO;
import com.shinhan.maahproject.dto.MyCardHiDTO;
import com.shinhan.maahproject.dto.MyCardNotByDTO;
import com.shinhan.maahproject.repository.BankRepository;
import com.shinhan.maahproject.repository.CardHistoryRepository;
import com.shinhan.maahproject.repository.MemberAccountRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.PointByRepository;
import com.shinhan.maahproject.vo.BankVO;
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

		int totalLimit = mchRepo.sumHiCardTotalLimitByMemberBYOwner(member, mhicard);

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


		List<MemberCardByVO> mbycards = (List<MemberCardByVO>) mcbRepo
				.findByMemberAndMemberByStatusAndConnectHiCardNotNullOrderByMemberByRank(member, 0);


		List<MyCardByDTO> myCardListBy = mbycards.stream()
				.map(memberCardByVO -> mapper.map(memberCardByVO, MyCardByDTO.class)).collect(Collectors.toList());

		return myCardListBy;
	}

	// 나의 낫바이카드 리스트
	public List<MyCardNotByDTO> getMyCardListNotBy(String memberId) {
		ModelMapper mapper = new ModelMapper();
		MemberVO member = mRepo.findById(memberId).orElse(null);

		List<MemberCardByVO> mNotbycards = (List<MemberCardByVO>) mcbRepo
				.findByMemberAndMemberByStatusAndConnectHiCardNullOrderByMemberByRank(member, 0);

		// System.out.println(mNotbycards.get(0).getMemberByNumber());

		List<MyCardNotByDTO> myCardListNotBy = mNotbycards.stream().map(memberCardByVO -> {
			MyCardNotByDTO dto = mapper.map(memberCardByVO, MyCardNotByDTO.class);

			// 시작일과 종료일 설정
			LocalDate currentDate = LocalDate.now();
			Timestamp startDate = Timestamp.valueOf(currentDate.withDayOfMonth(1).atStartOfDay());
			Timestamp endDate = Timestamp.valueOf(currentDate
					.withDayOfMonth(currentDate.getMonth().length(currentDate.isLeapYear())).atTime(23, 59, 59));

			System.out.println(currentDate);
			System.out.println(startDate);
			System.out.println(endDate);

			Integer thisMonthSum = chRepo.findByMemberCardBy(startDate, endDate, memberCardByVO);

			System.out.println(thisMonthSum);

			int sum = (thisMonthSum != null) ? thisMonthSum : 0; // null 체크하여 기본값 할당

			dto.setThisMonthSum(sum);

			return dto;
		}).collect(Collectors.toList());

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

	public int reportLost(String memberCardNumber) {
		System.out.println("여기옴모모오ㅑ로매ㅑㅗㄹ니ㅏㅓㅏㅣㄹㄴ어");
		System.out.println(memberCardNumber);
		
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
