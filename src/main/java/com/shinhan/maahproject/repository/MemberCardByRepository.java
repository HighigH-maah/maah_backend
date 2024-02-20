package com.shinhan.maahproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.maahproject.dto.MyCardByDTO;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;
import com.shinhan.maahproject.vo.ByCardVO;


public interface MemberCardByRepository extends CrudRepository<MemberCardByVO, String> {
	
	@Query("SELECT DISTINCT mb FROM MemberCardByVO mb "
			+ "JOIN FETCH mb.byCard b "
			+ "JOIN FETCH b.benefits bb "
			+ "JOIN FETCH bb.benefits bf "
			+ "WHERE mb.connectHiCard = :connectedHiCard "
			+ "AND mb.memberByStatus = 0")
	List<MemberCardByVO> findByConnectHiCard(@Param("connectedHiCard") MemberCardHiVO connectHiCard);
	
	
	List<MemberCardByVO> findByMemberAndMemberByStatusAndConnectHiCardNotNullOrderByMemberByRank(MemberVO member, int memberByStatus);


	List<MemberCardByVO> findByMemberAndMemberByStatus(MemberVO member, int memberByStatus);
	
	List<MemberCardByVO> findByMemberAndMemberByStatusAndByCard(MemberVO member, int memberByStatus, ByCardVO byCard);

	List<MemberCardByVO> findByMemberAndMemberByStatusAndConnectHiCardNullOrderByMemberByRank(MemberVO member, int memberByStatus);

	List<MemberCardByVO> findByMemberAndMemberByStatusOrderByMemberByRank(MemberVO member, int memberByStatus);
	
	List<MemberCardByVO> findByMemberByNumber(String memberByNumber);
	
	List<MemberCardByVO> findByMemberAndMemberByNumberAndMemberByStatus(MemberVO member, String memberByNumber, int memberByStatus);

}
