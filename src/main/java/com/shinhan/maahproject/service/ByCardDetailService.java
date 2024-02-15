package com.shinhan.maahproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.ByCardBenefitsDTO;
import com.shinhan.maahproject.dto.ByCardDetailDTO;
import com.shinhan.maahproject.dto.HiCardBenefitsDTO;
import com.shinhan.maahproject.repository.BenefitCategoryRepository;
import com.shinhan.maahproject.repository.ByBenefitRepository;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.repository.ByRelationBenefitRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.vo.BenefitCategoryVO;
import com.shinhan.maahproject.vo.ByBenefitVO;
import com.shinhan.maahproject.vo.ByCardVO;
import com.shinhan.maahproject.vo.ByRelationBenefitVO;
import com.shinhan.maahproject.vo.MemberBenefitVO;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;

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
	ByCardRepository bycRepo;
	
	@Autowired
	ByBenefitRepository bybRepo;
	
	
	//By:Card 상세 정보 가져오기
	public ByCardDetailDTO getByCardInfo(String memberId) {
		ByCardDetailDTO bydto = new ByCardDetailDTO();
		MemberCardByVO mbycard = null;
		
		MemberVO member = mRepo.findById(memberId).orElse(null);
		
		if (member != null) {
			for(MemberCardByVO bycard : mbRepo.findByMemberAndMemberByStatus(member, 0)) {
				mbycard = bycard;
				
				if(bycard.getMemberByStatus() == 0) {
					bydto.setByName(bycard.getByCard().getByName()); //byName
					bydto.setByImagePath(bycard.getByCard().getByImagePath()); //byImagePath
					bydto.setMemberCardByNickname(bycard.getMemberCardByNickname()); //memberCardByNickname
					//bycard.getPointBys()
					
					
					ByBenefitVO bybenefits = bbRepo.findByCards(bycard.getByCard()).get(0).getBenefits();
					bydto.setByBenefitMinCondition(bybenefits.getByBenefitMinCondition()); //byBenefitMinCondition
				}
			}
		}
		
		return bydto;
	}
	
	public Map<Integer, List<ByCardBenefitsDTO>> getAllByCardBenefits(String memberId){
		
		List<ByCardBenefitsDTO> bybenefitList = new ArrayList<>(); //결과를 저장할 리스트 생성
		
 		MemberVO member = mRepo.findById(memberId).orElse(null); // 회원 정보 조회

		Map<Integer, List<ByCardBenefitsDTO>> dtoMap = new HashMap<>();
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
 						
 						if(dtoMap.get(dto.getByCode())==null){
 							dtoMap.put(dto.getByCode(), new ArrayList<>());
 						}
 						dtoMap.get(dto.getByCode()).add(dto);
 						
 					}
 				}
 			}
 			System.out.println(dtoMap.toString());
 		}
		
 		return dtoMap;
	}
	
//	public ByCardBenefitsDTO getByCardBenefits(String memberId){
//		
//		ByCardBenefitsDTO bybenefitDTO = new ByCardBenefitsDTO(); //결과를 저장할 리스트 생성
//		
// 		MemberVO member = mRepo.findById(memberId).orElse(null); // 회원 정보 조회
//		
// 		if (member != null) {
// 			for(MemberCardByVO bycard : mbRepo.findByMemberAndMemberByStatus(member, 0)) {
// 				if(bycard.getMemberByStatus() == 0) {
// 					for(ByRelationBenefitVO bvo : bbRepo.findByCards(bycard.getByCard())) {
// 						String benefitName = bvo.getBenefits().getByBenefitCategory().getBenefitName();
// 						String benefitDesc = bvo.getBenefits().getByBenefitDesc();
// 						String benefitBody = bvo.getBenefits().getByBenefitBody();
// 						
// 						bybenefitDTO.addBenefit(benefitName, benefitDesc, benefitBody);
// 					}
// 				}
// 			}
// 		}
//		
// 		return bybenefitDTO;
//	}
}
