package com.shinhan.maahproject.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.ByCardDTO;
import com.shinhan.maahproject.repository.BenefitCategoryRepository;
import com.shinhan.maahproject.repository.ByBenefitRepository;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.repository.ByRelationBenefitRepository;
import com.shinhan.maahproject.vo.BenefitCategoryVO;
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

    @Autowired
    BenefitCategoryRepository bcRepo;

    public List<ByCardDTO> getAllByCard() {
        ModelMapper mapper = new ModelMapper();
        List<ByCardDTO> ByCardList = new ArrayList<>();
        Iterable<ByCardVO> byCardVOIterable = bRepo.findAll();

        for (ByCardVO byCardVO : byCardVOIterable) {
            List<ByRelationBenefitVO> brbMultiList = brbRepo.findByCards(byCardVO);
            String clist = byCardVO.getByCategoryList();
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
                            //log.warn("Benefit category not found for ID: " + num);
                        }
                    } catch (NumberFormatException e) {
                        //log.error("Invalid number format: " + number, e);
                    }
                }
            }

            List<String> blist = new ArrayList<>(); // 혜택 문장들을 모두 담는다

            for (ByRelationBenefitVO brbMulti : brbMultiList) {
                blist.add(brbMulti.getBenefits().getByBenefitDesc());
            }

            ByCardDTO bDto = mapper.map(byCardVO, ByCardDTO.class);
            bDto.setBenefitList(blist); // 혜택을 다음과 같이 세팅
            bDto.setByCategoryList(categoryToDesc);

            ByCardList.add(bDto);
            //log.info(ByCardList.toString());
        }
        return ByCardList;
    }
}
