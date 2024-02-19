package com.shinhan.maahproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.MyCardByDTO;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.repository.HiCardImageRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.vo.ByCardVO;

@Service
public class CardApplicationService {
	
	@Autowired
	MyCardListService mclService;
	
	@Autowired
	HiCardImageRepository hicardRepo;
	
	@Autowired
	ByCardRepository bycardRepo;
	
	@Autowired
	MemberRepository memberRepo;

	public Map<String, Object> getHicardDesigns(String userId) {
		Map<String, Object> res = new HashMap<>();
		List<Map<String, String>> by = new ArrayList<>();
		List<String> img = new ArrayList<>();
		
		for(MyCardByDTO my : mclService.getMyCardListBy(userId)) {
			Map<String, String> myCard = new HashMap<>();
			myCard.put("nickname", my.getMemberCardByNickname());
			myCard.put("image", my.getByImagePath());
			img.add(my.getByImagePath());
			by.add(myCard);
		}
		
		int length = by.size() % 5 == 0 ? 0 : 5 - (by.size() % 5);
		length = by.size() == 0 ? 5 : length;
		if(length > 0) {
			List<ByCardVO> list = (List<ByCardVO>) bycardRepo.findAll();
			for(int i = 0; i < length; i++) {
				while(true) {
					int num = (int) (Math.random() * list.size());
					if(!img.contains(list.get(num).getByImagePath())) {
						Map<String, String> myCard = new HashMap<>();
						myCard.put("nickname", list.get(num).getByName());
						myCard.put("image", list.get(num).getByImagePath());
						img.add(myCard.get("image"));
						by.add(myCard);
						break;
					}
				}
			}
		}
		
		res.put("hi", hicardRepo.findAll());
		res.put("by", by);
		
		return res;
	}

}
