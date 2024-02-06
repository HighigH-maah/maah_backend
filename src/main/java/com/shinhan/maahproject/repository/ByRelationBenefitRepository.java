package com.shinhan.maahproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shinhan.maahproject.vo.ByCardVO;
import com.shinhan.maahproject.vo.ByRelationBenefitMultikey;
import com.shinhan.maahproject.vo.ByRelationBenefitVO;
import java.util.List;

@Repository
public interface ByRelationBenefitRepository extends CrudRepository<ByRelationBenefitVO, ByRelationBenefitMultikey> {
	
	@Query("select * from ByRelationBenefitVO relation where "
			+ "byRelationBenefitKey.byRelateBenefitCode = %?1%")
	List<Object[]> selectByByCardCode(ByCardVO ByCardCode);
//	List<ByRelationBenefitMultikey> findByByCardCode(ByCardVO byCardCode);
}
