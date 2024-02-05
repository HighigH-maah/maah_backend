package com.shinhan.maahproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
	@Autowired
	MemberRepository mRepo;
	
	public MemberDTO getMember(String member_id) {
		ModelMapper mapper = new ModelMapper();
		return mRepo.findById(member_id)
		        .map(memvo -> mapper.map(memvo, MemberDTO.class))
		        .orElse(null);
	}

}
