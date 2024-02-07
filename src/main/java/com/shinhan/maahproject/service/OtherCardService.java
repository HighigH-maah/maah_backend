package com.shinhan.maahproject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.BankDTO;
import com.shinhan.maahproject.dto.OtherCardDTO;
import com.shinhan.maahproject.repository.OtherCardRepository;
import com.shinhan.maahproject.vo.OtherCardVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OtherCardService {
	@Autowired
	OtherCardRepository orepo;
	
	public List<OtherCardVO> getOtherCards(String company, int category) {
	    ModelMapper mapper = new ModelMapper();

	    List<OtherCardVO> oCards = orepo.findByOtherCompanyBankNameAndOtherCategoryListContaining(company, category);
	    
	 

	   
	    return oCards;
	}
}
