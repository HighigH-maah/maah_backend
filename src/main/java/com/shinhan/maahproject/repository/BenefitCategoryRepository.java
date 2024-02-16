package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.BenefitCategoryVO;
import java.util.List;


public interface BenefitCategoryRepository extends CrudRepository<BenefitCategoryVO, Integer>{

	List<BenefitCategoryVO> findByBenefitCode(int benefitCode);
}
