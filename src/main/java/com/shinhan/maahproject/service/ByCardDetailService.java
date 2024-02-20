package com.shinhan.maahproject.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.AccountChangeDTO;
import com.shinhan.maahproject.dto.ByCardBenefitsDTO;
import com.shinhan.maahproject.dto.ByCardDetailDTO;
import com.shinhan.maahproject.dto.HiCardBenefitsDTO;
import com.shinhan.maahproject.dto.CardHistoryDTO;
import com.shinhan.maahproject.repository.BenefitCategoryRepository;
import com.shinhan.maahproject.repository.ByBenefitRepository;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.repository.ByRelationBenefitRepository;
import com.shinhan.maahproject.repository.CardHistoryRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.PointByRepository;
import com.shinhan.maahproject.vo.BenefitCategoryVO;
import com.shinhan.maahproject.vo.ByBenefitVO;
import com.shinhan.maahproject.vo.ByCardVO;
import com.shinhan.maahproject.vo.ByRelationBenefitVO;
import com.shinhan.maahproject.vo.CardHistoryVO;
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
	
	@Autowired
	CardHistoryRepository cRepo;
	
	@Autowired
	ByCardRepository bcRepo;
	
	//By:Card 해택 정보
	public List<ByCardBenefitsDTO> getByCardBenefits(int byCardCode){
		List<ByCardBenefitsDTO> bybenefitList = new ArrayList<>();
		
		for(ByCardVO bycvo : bcRepo.findByByCode(byCardCode)) {
			int byCode = bycvo.getByCode();
			
			for(ByRelationBenefitVO bvo : bbRepo.findByCards(bycvo)) {
				
				String benefitName = bvo.getBenefits().getByBenefitCategory().getBenefitName();
				String benefitDesc = bvo.getBenefits().getByBenefitDesc();
				String benefitBody = bvo.getBenefits().getByBenefitBody();
				
				ByCardBenefitsDTO dto = new ByCardBenefitsDTO(byCode, benefitName, benefitDesc, benefitBody);
				bybenefitList.add(dto);
				
			}
		}
	
		return bybenefitList;
	}
	
	//By:Card 상품 정보
	public ByCardDetailDTO getByCardInfo(int byCardCode) {
		ByCardDetailDTO bycarddto = new ByCardDetailDTO();
		
		for(ByCardVO bycvo : bcRepo.findByByCode(byCardCode)) {
			bycarddto.setByCode(bycvo.getByCode());			
			bycarddto.setByImagePath(bycvo.getByImagePath()); 
			bycarddto.setByName(bycvo.getByName());
	        bycarddto.setByBenefitMinCondition(bycvo.getByMinLimit()); // 최솟값 설정
		};
		
		return bycarddto;
	}
	
	
	//나의 By:Card
	
	//Member By Card 인지의 유뮤
	//0이 true(user3이 hiCard에 연결된 byCard임) 1이 false(user3이 hiCard에 연결 안 된 byCard임)
	//1. member의 member_by_card를 다 가져온다. 
	//2. member_by_card에서 member_by_connect_hi가 null인지 아닌지 판단한다.
	//3. 전부 null이면 byCard가 없는 이용자이다. 아니면 byCard가 하나 이상 있는 이용자이다.
	public int isConnectHiOrNot(String memberId, String memberByNumber) {
		
		 MemberVO member = mRepo.findById(memberId).orElse(null);
		    
		    if (member == null) {
		        // memberId에 해당하는 member가 없는 경우
		        return 0;
		    }
		    
		    List<MemberCardByVO> memberCardByList = mbRepo.findByMemberAndMemberByNumberAndMemberByStatus(member, memberByNumber, 0);
		    
		    if (memberCardByList.isEmpty()) {
		        // member와 memberByNumber에 해당하는 MemberCardByVO가 없는 경우
		        return 0;
		    }
		    
		    // MemberCardByVO 목록을 반복하면서 connectHiCard가 null인지 확인
		    for (MemberCardByVO mcbvo : memberCardByList) {
		        MemberCardHiVO connectHiCard = mcbvo.getConnectHiCard();
		        if (connectHiCard == null || connectHiCard.getMemberHiNumber() == null) {
		            return 0; // connectHiCard가 null이거나 memberHiNumber가 null인 경우
		        } else {
		            return 1; // connectHiCard가 null이 아니고 memberHiNumber가 null이 아닌 경우
		        }
		    }
		    
		    // 여기까지 왔다면 모든 MemberCardByVO의 connectHiCard가 null인 경우
		    return 0;
		
	}
	
	public int getMyBycardCode(String memberId, String memberByNumber) {
		int byCode = 0;
		
		MemberVO member = mRepo.findById(memberId).orElse(null);
		for(MemberCardByVO mcbvo : mbRepo.findByMemberAndMemberByNumberAndMemberByStatus(member, memberByNumber, 0)) {
			byCode = mcbvo.getByCard().getByCode();
		}
		
		return byCode;
	}
	
	//By:Card 결제 이력 가져오기
	public Map<Integer, List<CardHistoryDTO>> getBycardHistory(String memberId) {
	    ModelMapper mapper = new ModelMapper();
	    List<CardHistoryDTO> bydtoList = new ArrayList<>(); // 결과를 저장할 리스트 생성
	    
	    MemberVO member = mRepo.findById(memberId).orElse(null); // 회원 정보 조회
	    
	    Map<Integer, List<CardHistoryDTO>> byCardHistorydtoMap = new HashMap<>();
	    
	    if (member != null) {
	    	for(MemberCardByVO bycard : mbRepo.findByMemberAndMemberByStatus(member, 0)) {
	    		for(ByCardVO bcvo : bcRepo.findByByCode(bycard.getByCard().getByCode())) {
	    			int byCodeNum = bcvo.getByCode();
	    			for (MemberCardByVO bycard2 : mbRepo.findByMemberAndMemberByStatusAndByCard(member, 0, bcvo)) {
	    				if (bycard2.getMemberByStatus() == 0) {
	    					// 바이카드 정보 설정
	    					List<CardHistoryVO> chvoList = cRepo.findByMemberCardBy(bycard);
		                
	    					for(CardHistoryVO chvo : chvoList) {
	    						// 각 카드 이력을 DTO로 변환하여 리스트에 추가
	    						
	    						int amount = chvo.getCardHistoryAmount();
	    						String store = chvo.getCardHistoryStore();
	    						Timestamp date = chvo.getCardHistoryDate();
	    						
	    						CardHistoryDTO bydto = new CardHistoryDTO(byCodeNum, store, date, amount);
	    						bydtoList.add(bydto);
	    						
	    						if(byCardHistorydtoMap.get(bydto.getByCode())==null){
	    							byCardHistorydtoMap.put(bydto.getByCode(), new ArrayList<>());
	     						}
	    						byCardHistorydtoMap.get(bydto.getByCode()).add(bydto);
	    						
	    					}
		                
	    					break; // 바이카드 정보를 찾았으므로 루프 종료
	    				}
	    			}
	    		}
	    	}
	    }
	    return byCardHistorydtoMap;
	}
	
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
	                    
	                    if(byBenefitMinCondition == 0) {
	                    	minByBenefitMinCondition = 0;
	                    	
	                    	break;
	                    }
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