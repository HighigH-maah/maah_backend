package com.shinhan.maahproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;

import java.sql.Timestamp;
import java.util.List;


public interface MemberCardHiRepository extends CrudRepository<MemberCardHiVO, String> {

	List<MemberCardHiVO> findByMemberHiOwnerAndMemberHiStatus(MemberVO memberHiOwner, int memberHiStatus);
	

	@Query("SELECT mc FROM MemberCardHiVO mc "
	        + "JOIN FETCH mc.cardHis ch "
	        + "JOIN FETCH mc.hiImageCode "
	        + "WHERE mc.memberHiOwner = :memberId "
	        + "AND mc.memberHiStatus = 0 "
	        + "AND ch.cardHistoryDate >= :startDate "
	        + "AND ch.cardHistoryDate <= :endDate ") // Include the missing column in the GROUP BY clause
	List<MemberCardHiVO> findByMemberHiOwnerWithHiImageCode(@Param("memberId") MemberVO member,
			@Param("startDate") Timestamp startDate,
	        @Param("endDate") Timestamp endDate);
	
//	@Query("SELECT SUM(mb.memberByLimit) FROM MemberCardByVO mb " +
//			  "WHERE mb.memberByOwner = :memberId " +
//			  "AND mb.connectHiCard = :connectHiCard")
//			Integer sumHiCardTotalLimitByMemberBYOwner (@Param("memberId") MemberVO member, @Param("connectHiCard") MemberCardHiVO connectHiCard);
	
	@Query("SELECT SUM(mb.memberByLimit) FROM MemberCardByVO mb "
			+ "WHERE mb.member = :memberId "
			+ "AND mb.connectHiCard = :connectHiCard")
			Integer sumHiCardTotalLimitByMemberBYOwner (@Param("memberId") MemberVO member, @Param("connectHiCard") MemberCardHiVO connectHiCard);


	MemberCardHiVO findByMemberHiOwnerAndMemberHiNumberAndMemberHiStatus(MemberVO member, String cardNum, int i);

}
