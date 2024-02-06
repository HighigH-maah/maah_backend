package com.shinhan.maahproject.service;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.ByCardDTO;
import com.shinhan.maahproject.dto.OtherCardDTO;
import com.shinhan.maahproject.repository.ByBenefitRepository;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.repository.ByRelationBenefitRepository;
import com.shinhan.maahproject.vo.ByBenefitVO;
import com.shinhan.maahproject.vo.ByCardVO;
import com.shinhan.maahproject.vo.ByRelationBenefitMultikey;
import com.shinhan.maahproject.vo.ByRelationBenefitVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ByCardService {
	@Autowired
	ByCardRepository bRepo;
	
	@Autowired
	ByRelationBenefitRepository brbRepo;
	
	@Autowired
	ByBenefitRepository bbRepo;
	
	public List<ByCardDTO> getAllByCard() {
	    ModelMapper mapper = new ModelMapper();
	    List<ByCardDTO> ByCardList = new ArrayList<>();
	    Iterable<ByCardVO> byCardVOIterable = bRepo.findAll();
	    for (ByCardVO byCardVO : byCardVOIterable) {
	    	log.info(byCardVO.toString());
	    	List<Object[]> brbMultiList = brbRepo.selectByByCardCode(byCardVO);
	    	for(Object brbMulti:brbMultiList) {
	    		ByBenefitVO tempvo = (ByBenefitVO) brbMulti;
	    		log.info(tempvo.getByBenefitDesc());
	    	}
	    	
	    	
	    	
	    	ByCardList.add(mapper.map(byCardVO, ByCardDTO.class));
	    }
	    return ByCardList;
	}

}
