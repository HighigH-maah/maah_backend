package com.shinhan.maahproject.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.AccountChangeDTO;
import com.shinhan.maahproject.dto.ByCardBenefitsDTO;
import com.shinhan.maahproject.dto.ByCardDetailDTO;
import com.shinhan.maahproject.dto.HiCardBenefitsDTO;
import com.shinhan.maahproject.repository.BenefitCategoryRepository;
import com.shinhan.maahproject.repository.ByBenefitRepository;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.repository.ByRelationBenefitRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.PointByRepository;
import com.shinhan.maahproject.vo.BenefitCategoryVO;
import com.shinhan.maahproject.vo.ByBenefitVO;
import com.shinhan.maahproject.vo.ByCardVO;
import com.shinhan.maahproject.vo.ByRelationBenefitVO;
import com.shinhan.maahproject.vo.MemberBenefitVO;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;
import com.shinhan.maahproject.vo.PointByMultikey;
import com.shinhan.maahproject.vo.PointByVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ByCardDetailService {

	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	MemberCardByRepository mbRepo;
	
	@Autowired
	ByRelationBenefitRepository bbRepo;
	
	@Autowired
	PointByRepository pbRepo;
	
	//By:Card 계좌 정보
		public AccountChangeDTO getByCardAccountInfo(String memberId) {
		    AccountChangeDTO acdto = new AccountChangeDTO(); // AccountChangeDTO 객체 생성

		    MemberVO member = mRepo.findById(memberId).orElse(null); // 회원 정보 조회

		    if (member != null) {
		        for (MemberCardByVO bycard : mbRepo.findByMemberAndMemberByStatus(member, 0)) {
		            if (bycard.getMemberByStatus() == 0) {
		                // 바이카드 정보 설정
		            	acdto.setBankName(bycard.getMemberAccountKey().getBank().getBankName()); //bankName
		            	acdto.setMemberByAccountNumber(bycard.getMemberAccountKey().getMemberAccountNumber());//accountNumber
		            	acdto.setMemberByNumber(bycard.getMemberByNumber()); //memberByNumber
		            	
		                break; // 하이카드 정보를 찾았으므로 루프 종료
		            }
		        }
		    }
		    return acdto;
		}
	
	//By:Card 상세 정보 가져오기
	public Map<Integer, List<ByCardDetailDTO>> getAllByCardInfo(String memberId) {
		
		List<ByCardDetailDTO> bycardInfoList = new ArrayList<>();
		
		//ByCardDetailDTO bydto = new ByCardDetailDTO();
		
		MemberVO member = mRepo.findById(memberId).orElse(null);
		
		Map<Integer, List<ByCardDetailDTO>> byCardDetaildtoMap = new HashMap<>();
		if (member != null) {
			for(MemberCardByVO bycard : mbRepo.findByMemberAndMemberByStatus(member, 0)) {
				if(bycard.getMemberByStatus() == 0) {
					String memberByNumber = bycard.getMemberByNumber(); 
					int byCode = bycard.getByCard().getByCode(); //byCode
					String byName = bycard.getByCard().getByName(); //byName
					String memberCardByNickname = bycard.getMemberCardByNickname(); //memberCardByNickname
					String byImagePath = bycard.getByCard().getByImagePath(); //byImagePath
					String pointByMonth = null;
					int pointByAmount = 0;
					String connectHiCard = null;
					
					 // 현재 년월 가져오기
	                String currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
					
					for(PointByVO pbvo : pbRepo.findByMemberByNumberAndPointByMonth(bycard, currentYearMonth)) {
						pointByMonth = pbvo.getPointByMonth(); //pointByMonth
						pointByAmount= pbvo.getPointByAmount(); //pointByAmount
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
					
					MemberCardHiVO connectHiCardNum = bycard.getConnectHiCard();
	                if (connectHiCardNum != null) {
	                	connectHiCard = connectHiCardNum.getMemberHiNumber();//connectHiCard
	                }
	                
	                ByCardDetailDTO dto = new ByCardDetailDTO(memberByNumber,byCode, byName, memberCardByNickname, byImagePath, pointByAmount, pointByMonth, byBenefitMinCondition, connectHiCard);
	                bycardInfoList.add(dto);
	                
	                if(byCardDetaildtoMap.get(dto.getByCode())==null){
	                	byCardDetaildtoMap.put(dto.getByCode(), new ArrayList<>());
					}
	                byCardDetaildtoMap.get(dto.getByCode()).add(dto);
				}
			}
		}
		
		return byCardDetaildtoMap;
	}
	
	public Map<Integer, List<ByCardBenefitsDTO>> getAllByCardBenefits(String memberId){
		
		List<ByCardBenefitsDTO> bybenefitList = new ArrayList<>(); //결과를 저장할 리스트 생성
		
 		MemberVO member = mRepo.findById(memberId).orElse(null); // 회원 정보 조회

		Map<Integer, List<ByCardBenefitsDTO>> byCardBenefitsdtoMap = new HashMap<>();
 		if (member != null) {
 			for(MemberCardByVO bycard : mbRepo.findByMemberAndMemberByStatus(member, 0)) {
 				if(bycard.getMemberByStatus() == 0) {
 					int byCode = bycard.getByCard().getByCode();
 					for(ByRelationBenefitVO bvo : bbRepo.findByCards(bycard.getByCard())) {
 						
 						String benefitName = bvo.getBenefits().getByBenefitCategory().getBenefitName();
 						String benefitDesc = bvo.getBenefits().getByBenefitDesc();
 						String benefitBody = bvo.getBenefits().getByBenefitBody();
 						
 						ByCardBenefitsDTO dto = new ByCardBenefitsDTO(byCode, benefitName, benefitDesc, benefitBody);
 						bybenefitList.add(dto);
 						
 						if(byCardBenefitsdtoMap.get(dto.getByCode())==null){
 							byCardBenefitsdtoMap.put(dto.getByCode(), new ArrayList<>());
 						}
 						byCardBenefitsdtoMap.get(dto.getByCode()).add(dto);
 						
 					}
 				}
 			}
 			//System.out.println(dtoMap.toString());
 		}
		
 		return byCardBenefitsdtoMap;
	}
}