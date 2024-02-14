package com.shinhan.maahproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.HiCardHistoryDTO;
import com.shinhan.maahproject.repository.CardHistoryRepository;
import com.shinhan.maahproject.repository.MemberAccountRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.vo.CardHistoryVO;
import com.shinhan.maahproject.vo.MemberAccountMultikey;
import com.shinhan.maahproject.vo.MemberAccountVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CardHistoryService {
	
	@Autowired
	CardHistoryRepository chRepo;
	
	@Autowired
	MemberAccountRepository mRepo;
	
	@Autowired
	MemberRepository mrRepo;
	
	public Long getHistory(String memberHiNumber){
		Long historyAmount = 0L; // 실제 사용량
		Long limitedAmount = 0L; // 카드 한도
		List<CardHistoryVO> chList = chRepo.findByMemberCardHiMemberHiNumber(memberHiNumber);
		for(CardHistoryVO amount:chList) {
			log.info("getCardHistoryId : "+amount.getCardHistoryId());
			log.info("amount : "+amount.getCardHistoryAmount());
			historyAmount+=amount.getCardHistoryAmount();
		}
		log.info("Sum of price : "+historyAmount);
		
		return historyAmount;
	}
	
	public List<MemberAccountVO> getAccount() {
		
		log.info(mRepo.findByMemberAccountMemberIdMemberId("user2").toString());
//		Optional<MemberAccountVO> memAccount = mRepo.findByMemberAccountMemberId(memId);
//		String memberAccountNumber = memAccount.map(MemberAccountVO::getMemberAccountKey)
//                .map(MemberAccountMultikey.)
//                .orElse(null);
//
//System.out.println("Member Account Number: " + memberAccountNumber);
		List<MemberAccountVO> mAccountList = mRepo.findByMemberAccountMemberIdMemberId("user2");
		
		return mRepo.findByMemberAccountMemberIdMemberId("user2");
	}
	

}
