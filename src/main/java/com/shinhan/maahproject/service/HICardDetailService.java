package com.shinhan.maahproject.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.AccountChangeDTO;
import com.shinhan.maahproject.dto.HiCardBenefitsDTO;
import com.shinhan.maahproject.dto.HiCardDetailDTO;
import com.shinhan.maahproject.dto.HiCardHistoryDTO;
import com.shinhan.maahproject.dto.VirtualCardInfoDTO;
import com.shinhan.maahproject.repository.BankRepository;
import com.shinhan.maahproject.repository.BenefitCategoryRepository;
import com.shinhan.maahproject.repository.ByBenefitRepository;
import com.shinhan.maahproject.repository.ByBenefitRepository;
import com.shinhan.maahproject.repository.CardHistoryRepository;
import com.shinhan.maahproject.repository.MemberAccountRepository;
import com.shinhan.maahproject.repository.MemberBenefitRepository;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.TempHiRepository;
import com.shinhan.maahproject.vo.BankVO;
import com.shinhan.maahproject.vo.BenefitCategoryVO;
import com.shinhan.maahproject.vo.ByBenefitVO;
import com.shinhan.maahproject.vo.CardHistoryVO;
import com.shinhan.maahproject.vo.MemberBenefitVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;
import com.shinhan.maahproject.vo.TempHiVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HICardDetailService {

	@Autowired
	TempHiRepository tRepo;

	@Autowired
	MemberCardHiRepository mhRepo;

	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	BankRepository bRepo;
	
	@Autowired
	MemberAccountRepository maRepo;
	
	@Autowired
	CardHistoryRepository cRepo;
	
	@Autowired
	MemberBenefitRepository mbRepo;
	
	@Autowired
	ByBenefitRepository bbRepo;
	
	@Autowired
	BenefitCategoryRepository bcRepo;
	
	//Hi:Card 해택 가져오기
	public List<HiCardBenefitsDTO> getHiCardBenefits(String memberId){
		List<HiCardBenefitsDTO> hibenefitList = new ArrayList<>(); //결과를 저장할 리스트 생성
		
		MemberVO member = mRepo.findById(memberId).orElse(null); // 회원 정보 조회
		
		if (member != null) {
			for(MemberBenefitVO mbvo : mbRepo.findByMemberBenefitMemberId(member)) {
				for(ByBenefitVO bbvo : bbRepo.findByByBenefitCode(mbvo.getMemberBenefitByBenefitCode().getByBenefitCode())) {
					
					String benefitName = null;
					String benefitDesc = bbvo.getByBenefitDesc();
					String benefitBody = bbvo.getByBenefitBody();
					
					for(BenefitCategoryVO bcvo : bcRepo.findByBenefitCode(bbvo.getByBenefitCategory().getBenefitCode())) {
						benefitName = bcvo.getBenefitName();
					}
					 // HiCardBenefitsDTO 객체 생성하여 리스트에 추가
	                HiCardBenefitsDTO dto = new HiCardBenefitsDTO(benefitName, benefitDesc, benefitBody);
	                hibenefitList.add(dto);
				}
				
			}
		}
		
		return hibenefitList;
	}
	
	//Hi:Card 연결 계좌 변경 => 은행명 가져오기
	public List<BankVO> getBankName(){
		
		List<BankVO> bankInfo = (List<BankVO>) bRepo.findAll();
		log.info(bankInfo.toString());
		
		return bankInfo;
	}
	
	//나의 결제 이력
	public List<HiCardHistoryDTO> getHicardHistory(String memberId) {
	    ModelMapper mapper = new ModelMapper();
	    List<HiCardHistoryDTO> hidtoList = new ArrayList<>(); // 결과를 저장할 리스트 생성
	    
	    MemberVO member = mRepo.findById(memberId).orElse(null); // 회원 정보 조회
	    
	    if (member != null) {
	        for (MemberCardHiVO hicard : mhRepo.findByMemberHiOwnerAndMemberHiStatus(member, 0)) {
	            if (hicard.getMemberHiStatus() == 0) {
	                // 하이카드 정보 설정
	                List<CardHistoryVO> chvoList = cRepo.findByMemberCardHi(hicard);
	                
	                for(CardHistoryVO chvo : chvoList) {
	                    // 각 카드 이력을 DTO로 변환하여 리스트에 추가
	                    HiCardHistoryDTO hidto = mapper.map(chvo, HiCardHistoryDTO.class);
	                    hidtoList.add(hidto);
	                }
	                
	                break; // 하이카드 정보를 찾았으므로 루프 종료
	            }
	        }
	    }
	    
	    return hidtoList;
	}

	//Hi:Card 상세 정보 가져오기
	public HiCardDetailDTO getHiCardInfo(String memberId) {
		HiCardDetailDTO hidto = new HiCardDetailDTO();
		
		MemberVO member = mRepo.findById(memberId).orElse(null); // 회원 정보 조회
		MemberCardHiVO mhicard = null;
		int totalLimit = 0;
		
		
		if (member != null) {
	        for (MemberCardHiVO hicard : mhRepo.findByMemberHiOwnerAndMemberHiStatus(member, 0)) {
	        	mhicard = hicard;
	        	totalLimit = mhRepo.sumHiCardTotalLimitByMemberBYOwner(member,mhicard);
	        	
	        	if (hicard.getMemberHiStatus() == 0) {
	                // 하이카드 정보 설정
	        		hidto.setMemberHiNumber(hicard.getMemberHiNumber());
	            	hidto.setHiCardImageFrontPath(hicard.getHiImageCode().getHiCardImageFrontPath());
	            	hidto.setMemberHiNickname(hicard.getMemberHiNickname());
	            	hidto.setMemberMileage(member.getMemberMileage());
	            	hidto.setClassBenefitName(member.getClassBenefit().getClassBenefitName());
	            	hidto.setCardApplyLimitAmount(totalLimit);
	            	
	                log.info(hicard.toString());
	                break; // 하이카드 정보를 찾았으므로 루프 종료
	            }
	        }
	    }
		
		return hidto;
	}
	
	// Hi:Card 상세 정보 가져오기
//	public HiCardDetailDTO getHiCardInfo(String memberId, String cardNum) {
//	    HiCardDetailDTO hidto = new HiCardDetailDTO();
//
//	    MemberVO member = mRepo.findById(memberId).orElse(null); // 회원 정보 조회
//	    MemberCardHiVO mhicard = null;
//	    int totalLimit = 0;
//
//	    if (member != null) {
//	        // 카드 번호에 따라 해당 카드를 조회
//	        mhicard = mhRepo.findByMemberHiOwnerAndMemberHiNumberAndMemberHiStatus(member, cardNum, 0);
//
//	        if (mhicard != null) {
//	            totalLimit = mhRepo.sumHiCardTotalLimitByMemberBYOwner(member, mhicard);
//
//	            // 하이카드 정보 설정
//	            hidto.setMemberHiNumber(mhicard.getMemberHiNumber());
//	            hidto.setHiCardImageFrontPath(mhicard.getHiImageCode().getHiCardImageFrontPath());
//	            hidto.setMemberHiNickname(mhicard.getMemberHiNickname());
//	            hidto.setMemberMileage(member.getMemberMileage());
//	            hidto.setClassBenefitName(member.getClassBenefit().getClassBenefitName());
//	            hidto.setCardApplyLimitAmount(totalLimit);
//
//	            log.info(mhicard.toString());
//	        }
//	    }
//
//	    return hidto;
//	}
	
	//Hi:Card 연결 계좌 변경
	public AccountChangeDTO getHiCardAccountInfo(String memberId) {
	    AccountChangeDTO acdto = new AccountChangeDTO(); // AccountChangeDTO 객체 생성

	    MemberVO member = mRepo.findById(memberId).orElse(null); // 회원 정보 조회

	    if (member != null) {
	        for (MemberCardHiVO hicard : mhRepo.findByMemberHiOwnerAndMemberHiStatus(member, 0)) {
	            if (hicard.getMemberHiStatus() == 0) {
	                // 하이카드 정보 설정
	                acdto.setBankName(hicard.getMemberAccountKey().getMemberAccountKey().getBank().getBankName());
	                acdto.setMemberHiAccountNumber(hicard.getMemberAccountKey().getMemberAccountKey().getMemberAccountNumber());
	                acdto.setMemberHiNumber(hicard.getMemberHiNumber());
	                log.info(hicard.toString());
	                break; // 하이카드 정보를 찾았으므로 루프 종료
	            }
	        }
	    }

	    return acdto;
	}
	
	//가상 카드 번호조회
	public VirtualCardInfoDTO getVirtualCardInfo(String memberId) {
		// repository로 가져오면 아직 VO라서, ModelMapper를 통과시켜서 DTO로 바꿔야 한다.
		ModelMapper mapper = new ModelMapper();
		
		VirtualCardInfoDTO vcdto = null;
		MemberCardHiVO tempmhvo = null;

		for (MemberCardHiVO hicard : mhRepo.findByMemberHiOwnerAndMemberHiStatus(mRepo.findById(memberId).orElse(null),
				0)) {
			if (hicard.getMemberHiStatus() == 0) {
				tempmhvo = hicard;
				log.info(tempmhvo.toString());
			}
		}

		List<TempHiVO> tempHis = tRepo.findByMemberCardHiAndTempHiStatus(tempmhvo, 0);

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		for (TempHiVO temphi : tempHis) {
			log.info(temphi.toString());
			if (temphi.getTempHiStatus() == 0
//					&& temphi.getTempHiExpdate().after(ts)
					) {
				vcdto = mapper.map(temphi, VirtualCardInfoDTO.class);
				log.info(vcdto.toString());
			}
		}
		return vcdto;
	}
	
	
}
