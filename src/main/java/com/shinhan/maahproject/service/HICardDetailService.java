package com.shinhan.maahproject.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.AccountChangeDTO;
import com.shinhan.maahproject.dto.HiCardDetailDTO;
import com.shinhan.maahproject.dto.HiCardHistoryDTO;
import com.shinhan.maahproject.dto.VirtualCardInfoDTO;
import com.shinhan.maahproject.repository.BankRepository;
import com.shinhan.maahproject.repository.CardHistoryRepository;
import com.shinhan.maahproject.repository.MemberAccountRepository;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.TempHiRepository;
import com.shinhan.maahproject.vo.BankVO;
import com.shinhan.maahproject.vo.CardHistoryVO;
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
	
	public List<BankVO> getBankName(){
		
		List<BankVO> bankInfo = (List<BankVO>) bRepo.findAll();
		log.info(bankInfo.toString());
		
		return bankInfo;
	}
	
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

	
	public HiCardDetailDTO getHiCardInfo(String memberId) {
		HiCardDetailDTO hidto = new HiCardDetailDTO();
		
		MemberVO member = mRepo.findById(memberId).orElse(null); // 회원 정보 조회
		
		if (member != null) {
	        for (MemberCardHiVO hicard : mhRepo.findByMemberHiOwnerAndMemberHiStatus(member, 0)) {
	            if (hicard.getMemberHiStatus() == 0) {
	                // 하이카드 정보 설정
	            	hidto.setHiCardImageFrontPath(hicard.getHiImageCode().getHiCardImageFrontPath());
	            	hidto.setMemberHiNickname(hicard.getMemberHiNickname());
	            	hidto.setMemberMileage(member.getMemberMileage());
	            	hidto.setClassBenefitName(member.getClassBenefit().getClassBenefitName());
	            	hidto.setCardApplyLimitAmount(hicard.getCardApplyCode().getCardApplyLimitAmount());
	            	
	                log.info(hicard.toString());
	                break; // 하이카드 정보를 찾았으므로 루프 종료
	            }
	        }
	    }
		
		return hidto;
	}
	
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
