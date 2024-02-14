package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.ByBenefitVO;
import java.util.List;


public interface ByBenefitRepository extends CrudRepository<ByBenefitVO, Integer> {

	List<ByBenefitVO> findByByBenefitCode(int byBenefitCode);
}
