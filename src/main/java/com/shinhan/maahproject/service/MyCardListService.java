package com.shinhan.maahproject.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
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
import com.shinhan.maahproject.dto.MyCardByDTO;
import com.shinhan.maahproject.dto.MyCardHiDTO;
import com.shinhan.maahproject.dto.MyCardNotByDTO;
import com.shinhan.maahproject.repository.BankRepository;
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

	public MyCardHiDTO getMyCardListHi(String memberId) {

		ModelMapper mapper = new ModelMapper();
		Map<String, Timestamp> thisMonth = getThisMonth();

		MemberVO member = mRepo.findById(memberId).orElse(null);

		List<MemberCardHiVO> mhicards = mchRepo.findByMemberHiOwnerWithHiImageCode(member,
				thisMonth.get("startTimestamp"), thisMonth.get("endTimestamp"));

		ClassBenefitVO cb = member.getClassBenefit();
		// log.info(cb.toString());

		MemberCardHiVO mhicard = null;

		int thisMounthSum = 0;
		int totalLimit = 0;

		for (MemberCardHiVO mc : mhicards) {
			mhicard = mc;
			totalLimit = mchRepo.sumHiCardTotalLimitByMemberBYOwner(member, mhicard);
			for (CardHistoryVO cardHistory : mc.getCardHis()) {
				thisMounthSum += cardHistory.getCardHistoryAmount();
			}

			break;
		}

//		List<MemberCardByVO> mbycards = (List<MemberCardByVO>) mcbRepo.findAll();
//		
//		for(MemberCardByVO mb:mbycards){
//				mbycard = mb;
//				
//				if(mbycard.getConnectHiCard() != null && mbycard.getMember().getMemberId() == memberId) {
//					totalLimit += mbycard.getMemberByLimit();
//			}
//			
//		}

		MyCardHiDTO resultMyHiCard = mapper.map(mhicard, MyCardHiDTO.class);
		resultMyHiCard.setThisMonthSum(thisMounthSum);
		resultMyHiCard.setTotalLimit(totalLimit);
		resultMyHiCard.setClassBenefitName(cb.getClassBenefitName());

		//log.info(resultMyHiCard.toString());

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

//	@Transactional
	public List<MyCardByDTO> getMyCardListBy(String memberId) {
		ModelMapper mapper = new ModelMapper();
		MemberVO member = mRepo.findById(memberId).orElse(null);
		// log.info(member.getMemberId());

		LocalDateTime now = LocalDateTime.now();
		YearMonth ym = YearMonth.from(now);

		String poinByMonth = ym.toString().replaceAll("-", "");
		//System.out.println(poinByMonth);

		// List<MyCardByDTO> mbycards = (List<MyCardByDTO>)
		// mcbRepo.findByMemberAndMemberByStatus(member, 0);
		List<MemberCardByVO> mbycards = (List<MemberCardByVO>) mcbRepo
				.findByMemberAndMemberByStatusAndConnectHiCardNotNullOrderByMemberByRank(member, 0);
		
//		List<MemberCardByVO> mbycards = (List<MemberCardByVO>) mcbRepo.findByMemberAndMemberByStatusOrderByMemberByRank(member, 0);

//		List<MemberCardByVO> mbycards = (List<MemberCardByVO>) mcbRepo.findByMemberByCard(member);
		
		//log.info("77777777777777777777777"+mbycards.toString());
		
		List<MyCardByDTO> myCardListBy = mbycards.stream()
				.map(memberCardByVO -> mapper.map(memberCardByVO, MyCardByDTO.class)).collect(Collectors.toList());
		
//		log.info(myCardListBy.toString());
//		List<PointByVO> pointBys = pbRepo
//				.findByMemberByNumberMonthMemberByNumberAndMemberByNumberMonthPointByMonth(memberId, memberId);

//		for (MyCardByDTO aa : myCardListBy) {
//			System.out.println(aa);
//		}
//		return null;
		return myCardListBy;
	}
	
	public List<MyCardNotByDTO> getMyCardListNotBy(String memberId) {
//		ModelMapper mapper = new ModelMapper();
//		MemberVO member = mRepo.findById(memberId).orElse(null);
//		
//		System.out.println("==========================================================");
//		List<MemberCardByVO> mNotbycards = (List<MemberCardByVO>) mcbRepo
//				.findByMemberAndMemberByStatusAndConnectHiCardNullOrderByMemberByRank(member, 0);
//		
////		List<MemberCardByVO> mNotbycards = (List<MemberCardByVO>) mcbRepo
////				.findByMemberAndMemberByStatusOrderByMemberByRank(member, 0);
//		
//		System.out.println(mNotbycards.toString());
//		
//		List<MyCardNotByDTO> myCardListNotBy = mNotbycards.stream()
//				.map(memberCardByVO -> mapper.map(memberCardByVO, MyCardNotByDTO.class)).collect(Collectors.toList());
//		
//		System.out.println(myCardListNotBy.toString());
//		
//		for (MyCardNotByDTO aa : myCardListNotBy) {
//			System.out.println(aa);
//		}
		
		return null;
	}

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

			MemberAccountVO account = MemberAccountVO.builder()
					.memberAccountNumber(accch.getBankName())
					.bank(bank)
					.memberAccountMemberId(member)
					.memberAccountBalance(0)
					.memberAccountRegdate(timestamp)
					.memberAccountStatus(0)
					.memberAccountPassword("1234")
					.build();

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

	public int excludeHiCard(MyCardByDTO myCardBy) {
		
		int result = 0;
		
		MemberVO member = mRepo.findById(myCardBy.getMemberId()).orElse(null);
		
		List<MemberCardHiVO> mhicards = mchRepo.findByMemberHiOwnerAndMemberHiStatus(member, 0);
		
		MemberCardHiVO mhicard = mhicards.get(0);
	
		return 0;
	}
	
}
