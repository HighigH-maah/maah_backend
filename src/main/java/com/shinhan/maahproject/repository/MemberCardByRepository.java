package com.shinhan.maahproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.maahproject.dto.MyCardByDTO;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;

public interface MemberCardByRepository extends CrudRepository<MemberCardByVO, String> {

	List<MemberCardByVO> findByMemberAndMemberByStatusAndConnectHiCardNotNullOrderByMemberByRank(MemberVO member, int memberByStatus);

	List<MemberCardByVO> findByMemberAndMemberByStatusAndConnectHiCardNullOrderByMemberByRank(MemberVO member, int memberByStatus);

	List<MemberCardByVO> findByMemberAndMemberByStatusOrderByMemberByRank(MemberVO member, int memberByStatus);
//	@Query("SELECT mb FROM MemberCardByVO mb "
////			+ "JOIN FETCH mb.pointBys "
//			+ "JOIN FETCH mb.byCard bc "
////			+ "JOIN FETCH bc.benefits "
//			+ "WHERE mb.member = :memberId "
//			+ "AND mb.memberByStatus = 0 "
//			+ "AND connectHiCard IS NOT NULL")
//	List<MemberCardByVO> findByMemberByCard(@Param("memberId") MemberVO member);
}
