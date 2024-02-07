package com.shinhan.maahproject.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.OtherCardVO;

public interface OtherCardRepository extends CrudRepository<OtherCardVO, Integer> {

    List<OtherCardVO> findByOtherCompanyBankNameAndOtherCategoryListContaining(String bankName, int categoryNumber);

	OtherCardVO findByOtherName(String cardName);
}