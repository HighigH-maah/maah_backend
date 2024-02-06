package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shinhan.maahproject.vo.ByBenefitVO;
import com.shinhan.maahproject.vo.ByCardVO;
import com.shinhan.maahproject.vo.ByRelationBenefitMultikey;
import com.shinhan.maahproject.vo.ByRelationBenefitVO;
import java.util.List;

@Repository
public interface ByRelationBenefitRepository extends CrudRepository<ByRelationBenefitVO, ByRelationBenefitMultikey> {
	List<ByRelationBenefitVO> findByCards(ByCardVO cards);
	
//	List<ByRelationBenefitVO> findByByCardCode(ByCardVO byCardCode);
	

}
