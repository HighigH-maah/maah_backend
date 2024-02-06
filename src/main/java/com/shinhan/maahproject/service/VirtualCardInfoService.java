package com.shinhan.maahproject.service;

import java.sql.Timestamp;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.VirtualCardInfoDTO;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.TempHiRepository;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.TempHiVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VirtualCardInfoService {

	@Autowired
	TempHiRepository tRepo;

	@Autowired
	MemberCardHiRepository mhRepo;

	@Autowired
	MemberRepository mRepo;

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

//		vcdto.setTempHiCvc(a.getTempHiCvc());
//		vcdto.setTempHiExpdate(a.getTempHiExpdate());
//		vcdto.setTempHiNumber(a.getTempHiNumber());

		return vcdto;
	}
}
