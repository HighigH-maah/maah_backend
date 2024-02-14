package com.shinhan.maahproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.ByCardDetailDTO;
import com.shinhan.maahproject.dto.HiCardDetailDTO;
import com.shinhan.maahproject.dto.OtherCardDTO;
import com.shinhan.maahproject.repository.BenefitCategoryRepository;
import com.shinhan.maahproject.repository.ByBenefitRepository;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.repository.ByRelationBenefitRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.OtherCardRepository;
import com.shinhan.maahproject.vo.BenefitCategoryVO;
import com.shinhan.maahproject.vo.ByBenefitVO;
import com.shinhan.maahproject.vo.ByCardVO;
import com.shinhan.maahproject.vo.ByRelationBenefitMultikey;
import com.shinhan.maahproject.vo.ByRelationBenefitVO;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;
import com.shinhan.maahproject.vo.OtherCardVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ByCardDetailService {

	@Autowired
	ByCardRepository bRepo;

	@Autowired
	ByRelationBenefitRepository brbRepo;

	@Autowired
	ByBenefitRepository bbRepo;

	@Autowired
	BenefitCategoryRepository bcRepo;

	@Autowired
	OtherCardRepository oRepo;
	
	@Autowired
	MemberCardByRepository mbRepo;
	
	@Autowired
	MemberRepository mRepo;
	
	// 카테고리 리스트를 설명으로 바꿔주는 함수
	private List<String> getCategoryDescriptions(String clist) {
	    List<String> categoryToDesc = new ArrayList<>();

	    if (clist != null) {
	        String[] categoryDescList = clist.split(", ");
	        for (String number : categoryDescList) {
	            try {
	                int num = Integer.parseInt(number.trim());
	                BenefitCategoryVO bc = bcRepo.findById(num).orElse(null);
	                if (bc != null) {
	                    categoryToDesc.add(bc.getBenefitName().toString());
	                } else {
	                    // log.warn("Benefit category not found for ID: " + num);
	                }
	            } catch (NumberFormatException e) {
	                // log.error("Invalid number format: " + number, e);
	            }
	        }
	    }

	    return categoryToDesc;
	}

	public List<ByCardDetailDTO> getAllByCard() {
	    ModelMapper mapper = new ModelMapper();
	    List<ByCardDetailDTO> ByCardList = new ArrayList<>();
	    Iterable<ByCardVO> byCardVOIterable = bRepo.findAll();

	    for (ByCardVO byCardVO : byCardVOIterable) {
	        List<ByRelationBenefitVO> brbMultiList = brbRepo.findByCards(byCardVO);
	        List<String> categoryToDesc = getCategoryDescriptions(byCardVO.getByCategoryList());

	        List<String> blist = new ArrayList<>(); // 혜택 문장들을 모두 담는다

	        for (ByRelationBenefitVO brbMulti : brbMultiList) {
	            blist.add(brbMulti.getBenefits().getByBenefitDesc());
	        }

	        ByCardDetailDTO bDto = mapper.map(byCardVO, ByCardDetailDTO.class);
	        bDto.setBenefitList(blist); // 혜택을 다음과 같이 세팅
	        bDto.setByCategoryList(categoryToDesc);

	        ByCardList.add(bDto);
	        // log.info(ByCardList.toString());
	    }
	    return ByCardList;
	}

	public List<ByCardDetailDTO> getByCardsByOther(String cardName) {
	    ModelMapper mapper = new ModelMapper();
	    List<ByCardDetailDTO> byCardList = new ArrayList<>();

	    OtherCardVO otherCard = oRepo.findByOtherName(cardName);
	    // log.info("찾은 다른 카드:" + otherCard.toString());
	    String otherCategoryList = otherCard.getOtherCategoryList();
	    Iterable<ByCardVO> byCardVOIterable = bRepo.findAll();
	    
	    for (ByCardVO byCardVO : byCardVOIterable) {
	        String byCategories = byCardVO.getByCategoryList(); // 각각 ByCategories
	        log.info("byCategories" + byCategories);
	        
	        if (containsAnyCategory(byCategories, otherCategoryList)) {
	            List<String> categoryToDesc = getCategoryDescriptions(byCategories);
	            ByCardDetailDTO byCardDTO = mapper.map(byCardVO, ByCardDetailDTO.class);
	            byCardDTO.setByCategoryList(categoryToDesc);
	            byCardList.add(byCardDTO);
	        }
	    }

	    return byCardList;
	}

	private boolean containsAnyCategory(String byCategories, String otherCategoryList) {
	    if (byCategories == null || otherCategoryList == null) {
	        return false;
	    }

	    String[] otherCategories = otherCategoryList.split(", ");
	    for (String otherCategory : otherCategories) {
	        if (byCategories.contains(otherCategory.trim())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	
	
	
}
