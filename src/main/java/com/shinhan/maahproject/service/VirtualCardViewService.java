package com.shinhan.maahproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.VirtualCardViewDTO;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.TempHiRepository;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.TempHiVO;

@Service
public class VirtualCardViewService {

	@Autowired
	TempHiRepository tRepo;
	@Autowired
	MemberCardHiRepository mhRepo;
	@Autowired
	MemberRepository mRepo;
	
//	public VirtualCardViewDTO getVirtualCardInfo(String member_id) {
//		//repository로 가져오면 아직 VO라서, ModelMapper를 통과시켜서 DTO로 바꿔야 한다.
//		
//		VirtualCardViewDTO vcdto = null;
//		MemberCardHiVO tempmhvo = null;
//		for(MemberCardHiVO mhvo : mhRepo.findByMember_hi_owner(mRepo.findById(member_id).orElse(null))) {
//			tempmhvo = mhvo;
//			break;
//		}
//				
//				
//		ModelMapper mapper = new ModelMapper();
//		for(TempHiVO tempvo : tRepo.findByMember_card_hiAndTemp_hi_status(tempmhvo, 0)) {
//			vcdto = mapper.map(tempvo, VirtualCardViewDTO.class);
//			break;
//		}
//
//		return vcdto;
//	}
	
	
//	public MemberDTO getMember(String member_id) {
//		ModelMapper mapper = new ModelMapper();
//		return mRepo.findById(member_id)
//		        .map(memvo -> mapper.map(memvo, MemberDTO.class))
//		        .orElse(null);
//	}

	public VirtualCardViewDTO getVirtualCardInfo(String member_id) {
		
		VirtualCardViewDTO vcdto = null;
		MemberCardHiVO tempmhvo = null;
		tempmhvo = mhRepo.findByMemberHiOwner(member_id);
		//String member_hi_number = tempmhvo.getMember_hi_number();
		
		TempHiVO a = tRepo.findBymember_card_hiAndtemp_hi_status(tempmhvo, 0);
		
		vcdto.setTemp_hi_cvc(a.getTemp_hi_cvc());
		vcdto.setTemp_hi_expdate(a.getTemp_hi_expdate());
		vcdto.setTemp_hi_number(a.getTemp_hi_number());
		
		
		return vcdto;
	}
}