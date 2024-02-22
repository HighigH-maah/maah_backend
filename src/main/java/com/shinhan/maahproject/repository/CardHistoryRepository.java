package com.shinhan.maahproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.maahproject.vo.CardHistoryVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;

import java.sql.Timestamp;
import java.util.List;
import com.shinhan.maahproject.vo.MemberCardByVO;



public interface CardHistoryRepository extends CrudRepository<CardHistoryVO, Long> {
	List<CardHistoryVO> findByMemberCardHi(MemberCardHiVO memberCardHi);
	
	List<CardHistoryVO> findByMemberCardBy(MemberCardByVO memberCardBy);
	
	@Query("SELECT SUM(ch.cardHistoryAmount) FROM CardHistoryVO ch " +
            "WHERE ch.cardHistoryDate >= :startDate " +
            "AND ch.cardHistoryDate < :endDate " +
            "AND ch.memberCardBy = :memberCardBy")
	Integer findByMemberCardBy(@Param("startDate") Timestamp startDate,
            @Param("endDate") Timestamp endDate, 
            @Param("memberCardBy") MemberCardByVO memberCardBy);

	 List<CardHistoryVO> findByMemberCardHiMemberHiNumber(String memberHiNumber);
	 
	 
	 
	 @Query("SELECT ch FROM CardHistoryVO ch "
	 		+ "WHERE ch.memberCardBy.memberByNumber = :memberCard ")
	 List<CardHistoryVO> findByMemberCardhistoryB(@Param("memberCard") String memberCardBy);
}
