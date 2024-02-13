package com.shinhan.maahproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.HiCardHistoryDTO;
import com.shinhan.maahproject.repository.CardHistoryRepository;
import com.shinhan.maahproject.vo.CardHistoryVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CardHistoryService {
	
	@Autowired
	CardHistoryRepository chRepo;
	
	public String getHistory(){
		
		return chRepo.findAll().toString();
	}
	

}
