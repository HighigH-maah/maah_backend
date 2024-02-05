package com.shinhan.maahproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.OtherCardDTO;
import com.shinhan.maahproject.repository.OtherCardRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OtherCardService {
	@Autowired
	OtherCardRepository orepo;
	
	public OtherCardDTO getOtherCard(Integer other_code) {
		ModelMapper mapper = new ModelMapper();
		return orepo.findById(other_code)
		        .map(othercardvo -> mapper.map(othercardvo, OtherCardDTO.class))
		        .orElse(null);
	}

}
