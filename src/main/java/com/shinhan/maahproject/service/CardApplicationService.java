package com.shinhan.maahproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.repository.HiCardImageRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.vo.HiCardImageVO;
import com.shinhan.maahproject.vo.MemberVO;

@Service
public class CardApplicationService {
	
	@Autowired
	HiCardImageRepository hicardRepo;
	
	@Autowired
	MemberCardByRepository bycardRepo;
	
	@Autowired
	MemberRepository memberRepo;

	public List<HiCardImageVO> getHicardDesigns(String userId) {
		List<HiCardImageVO> list = (List<HiCardImageVO>) hicardRepo.findAll();
		return list;
	}

}
