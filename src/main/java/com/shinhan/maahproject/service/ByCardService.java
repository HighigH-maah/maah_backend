package com.shinhan.maahproject.service;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.ByCardDTO;
import com.shinhan.maahproject.dto.OtherCardDTO;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.vo.ByCardVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ByCardService {
	@Autowired
	ByCardRepository bRepo;
	
	public List<ByCardDTO> getAllByCard() {
	    ModelMapper mapper = new ModelMapper();
	    List<ByCardDTO> ByCardList = new ArrayList<>();
	    Iterable<ByCardVO> byCardVOIterable = bRepo.findAll();
	    for (ByCardVO byCardVO : byCardVOIterable) {
	    	ByCardList.add(mapper.map(byCardVO, ByCardDTO.class));
	    }
	    return ByCardList;
	}

}