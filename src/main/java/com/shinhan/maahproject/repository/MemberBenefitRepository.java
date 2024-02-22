package com.shinhan.maahproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.maahproject.vo.MemberBenefitVO;
import com.shinhan.maahproject.vo.MemberVO;

import java.util.List;


public interface MemberBenefitRepository extends CrudRepository<MemberBenefitVO, Long>{

	List<MemberBenefitVO> findByMemberBenefitMemberId(MemberVO memberBenefitMemberId);
	
	@Query("SELECT mb from MemberBenefitVO mb "
			+ "LEFT JOIN FETCH mb.memberBenefitByBenefitCode "
			+ "WHERE mb.memberBenefitMemberId.memberId = :memberId ")
	List<MemberBenefitVO> findByMemberBenefitMemberIdMemberId(@Param("memberId") String memberBenefitMemberId);
	
	
	
	@Query("DELETE FROM MemberBenefitVO mb "
			+ "WHERE mb.memberBenefitMemberId = :memberId")
	void deleteBenefitByMember(@Param("memberId") MemberVO member);
}
