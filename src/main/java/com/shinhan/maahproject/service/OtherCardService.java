package com.shinhan.maahproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.OtherCardDTO;
import com.shinhan.maahproject.repository.OtherCardRepository;
import com.shinhan.maahproject.vo.OtherCardVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OtherCardService {
	@Autowired
	OtherCardRepository orepo;
	
	public OtherCardDTO getOtherCard(Integer otherCode) {
		log.info(otherCode.toString());
		ModelMapper mapper = new ModelMapper();
		OtherCardVO oCard = orepo.findById(otherCode).orElse(null);
		log.info(oCard.toString());
		OtherCardDTO oDto = mapper.map(oCard, OtherCardDTO.class);
		log.info(oDto.toString());
		        
		 
		 return oDto;
	}

}
