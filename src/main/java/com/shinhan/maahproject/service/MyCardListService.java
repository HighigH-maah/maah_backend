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
import com.shinhan.maahproject.repository.MemberAccountRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.vo.CardHistoryVO;
import com.shinhan.maahproject.vo.ClassBenefitVO;
import com.shinhan.maahproject.vo.MemberAccountVO;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;


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
	
	public MyCardHiDTO getMyCardListHi(String memberId){
	
		ModelMapper mapper = new ModelMapper();	
		Map<String, Timestamp> thisMonth = getThisMonth();
		
		MemberVO member = mRepo.findById(memberId).orElse(null);
		
		List<MemberCardHiVO> mhicards = mchRepo.findByMemberHiOwnerWithHiImageCode(member, thisMonth.get("startTimestamp"), thisMonth.get("endTimestamp"));
		//log.info(mhicards.toString());
		
		ClassBenefitVO cb = member.getClassBenefit();
		//log.info(cb.toString());
		
		MemberCardHiVO mhicard = null;
		
		int thisMounthSum = 0;
		int totalLimit = 0;
		
		for(MemberCardHiVO mc:mhicards) {
				mhicard = mc;
				totalLimit = mchRepo.sumHiCardTotalLimitByMemberBYOwner(member,mhicard);
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
	
	public Map<String, Timestamp> getThisMonth(){
		
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

	public List<MyCardByDTO> getMyCardListBy(String memberId) {
		ModelMapper mapper = new ModelMapper();	
		MemberVO member = mRepo.findById(memberId).orElse(null);
		log.info(member.getMemberId());
		
		//List<MyCardByDTO> mbycards = (List<MyCardByDTO>) mcbRepo.findByMemberAndMemberByStatus(member, 0);
		List<MemberCardByVO> mbycards = (List<MemberCardByVO>) mcbRepo.findByMemberAndMemberByStatusAndConnectHiCardNotNullOrderByMemberByRank(member, 0);
		
		List<MyCardByDTO> myCardListBy = mbycards.stream()
		        .map(memberCardByVO -> mapper.map(memberCardByVO, MyCardByDTO.class))
		        .collect(Collectors.toList());
		
		for(MyCardByDTO aa:myCardListBy) {
			System.out.println(aa);
		}
		
		return myCardListBy;
	}

	@Transactional
	public int updateHiAccount(AccountCheckDTO accch) {
		int result = 0;
		
		log.info(accch.toString());
		//		 accch.getMemberId()
//		
//		result = mchRepo.updateHiAccount(accch);
		List<MemberCardHiVO> mhicards = mchRepo.findByMemberHiOwnerAndMemberHiStatus(mRepo.findById(accch.getMemberId()).orElse(null), 0);
		
		
		List<MemberAccountVO> mAccounts = maRepo.findByMemberAccountNumberAndBankBankCode(accch.getBankName(), accch.getBankCode());
		log.info(mAccounts.toString());
		//신규 등록 필요x
		if(mAccounts.size()!=0) {
			MemberAccountVO mAcc = mAccounts.get(0);
			log.info(mAcc.toString());
			for(MemberCardHiVO mc:mhicards) {
				mc.setMemberAccountKey(mAcc);
				log.info("dddd"+mc.toString());
			}
		}
		//신규 등록 필요
		else {
			log.info("신규 등록 필요");;
		}
		
		

		
		return result;
	}
	
}
