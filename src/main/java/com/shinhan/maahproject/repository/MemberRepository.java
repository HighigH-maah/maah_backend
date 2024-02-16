package com.shinhan.maahproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;

import java.sql.Timestamp;
import java.util.List;


public interface MemberRepository extends CrudRepository<MemberVO, String> {
	
//	@Query("SELECT m FROM MemberVO m "
////			+ "LEFT JOIN FETCH m.memberBenefits mb "
//			+ "WHERE m.memberId = :memberId ")
//		public MemberVO findByMemberIdWithBenefits(@Param("memberId") String memberId);

	List<MemberVO> findByMemberId(String memberId);

	
//	@Query("SELECT m FROM MemberVO m "
//			+ "JOIN FETCH m.memberHiCard mc "
//			+ "WHERE m.memberId = :memberId "
//			+ "AND mc.memberHiStatus = 0")
//	MemberVO findByMemberHiOwner(@Param("memberId") String memberId);
}

