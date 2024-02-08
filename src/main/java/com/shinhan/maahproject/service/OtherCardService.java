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
        List<OtherCardVO> oCards = orepo.findByOtherCompanyBankNameAndOtherCategoryListContaining(company, category);

        List<OtherCardVO> filteredOCards = oCards.stream()
                .filter(oCard -> company.equals(oCard.getOtherCompany().getBankName()) && hasCategory(oCard, category))
                .collect(Collectors.toList());

        return filteredOCards;
    }

    private boolean hasCategory(OtherCardVO oCard, int category) {
        String categoryList = "," + oCard.getOtherCategoryList() + ",";
        String categoryToCheck = "," + String.valueOf(category) + ",";
        return categoryList.contains(categoryToCheck);
    }
}