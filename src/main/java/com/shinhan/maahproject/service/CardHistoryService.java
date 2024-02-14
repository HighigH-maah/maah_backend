package com.shinhan.maahproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.HiCardHistoryDTO;
import com.shinhan.maahproject.repository.CardHistoryRepository;
import com.shinhan.maahproject.repository.MemberAccountRepository;
import com.shinhan.maahproject.vo.CardHistoryVO;
import com.shinhan.maahproject.vo.MemberAccountMultikey;
import com.shinhan.maahproject.vo.MemberAccountVO;
import com.shinhan.maahproject.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CardHistoryService {
	
	@Autowired
	CardHistoryRepository chRepo;
	
	@Autowired
	MemberAccountRepository mRepo;
	
	public String getHistory(){
		
		return chRepo.findAll().toString();
	}
	
	public Optional<MemberAccountVO> getAccount() {
		MemberVO memId= new MemberVO();
		memId.setMemberId("user2");
		
		//log.info(mRepo.findByMemberAccountMemberId(memId).toString());
		
//		Optional<MemberAccountVO> memAccount = mRepo.findByMemberAccountMemberId(memId);
//		String memberAccountNumber = memAccount.map(MemberAccountVO::getMemberAccountKey)
//                .map(MemberAccountMultikey.)
//                .orElse(null);
//
//System.out.println("Member Account Number: " + memberAccountNumber);
		return mRepo.findByMemberAccountMemberId(memId);
	}
	

}
