package com.shinhan.maahproject.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.shinhan.maahproject.dto.VirtualCardInfoDTO;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.TempHiRepository;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.TempHiVO;

public class VirtualCardInfoService {

	@Autowired
	TempHiRepository tRepo;
	
	@Autowired
	MemberCardHiRepository mhRepo;
	
	@Autowired
	MemberRepository mRepo;
	
public VirtualCardInfoDTO getVirtualCardInfo(String member_id) {
	//repository로 가져오면 아직 VO라서, ModelMapper를 통과시켜서 DTO로 바꿔야 한다.
	//ModelMapper mapper = new ModelMapper();
		
	VirtualCardInfoDTO vcdto = null;
		MemberCardHiVO tempmhvo = null;
		tempmhvo = mhRepo.findByMemberHiOwner(member_id);
		String member_hi_number = tempmhvo.getMember_hi_number();
		
		TempHiVO a = tRepo.findByMemberCardHiAndTempHiStatus(member_hi_number, 0);
		
		vcdto.setTemp_hi_cvc(a.getTemp_hi_cvc());
		vcdto.setTemp_hi_expdate(a.getTemp_hi_expdate());
		vcdto.setTemp_hi_number(a.getTemp_hi_number());
		
		
		return vcdto;
	}
}

