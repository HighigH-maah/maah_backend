package com.shinhan.maahproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.PointByMultikey;
import com.shinhan.maahproject.vo.PointByVO;
import java.util.List;
import com.shinhan.maahproject.vo.MemberCardByVO;




public interface PointByRepository extends CrudRepository<PointByVO, PointByMultikey> {

	List<PointByVO> findByMemberByNumber(MemberCardByVO memberByNumber);
	
	List<PointByVO> findByMemberByNumberAndPointByMonth(MemberCardByVO memberByNumber, String pointByMonth);

//	List<PointByVO> findByMemberByNumberMonthMemberByNumberAndMemberByNumberMonthPointByMonth(String memberByNumber, String pointByMonth);

	
	
	
	
	
	
	
	@Query("SELECT pb FROM PointByVO pb "
			+ "WHERE pb.memberByNumber "
			+ "IN :memberByNumberList "
			+ "AND pb.pointByMonth = :pointByMonth "
			+ "ORDER BY pb.memberByNumber.memberByRank")
	List<PointByVO> findByMemberByNumberListAndPointByMonth(@Param("memberByNumberList") List<MemberCardByVO> memberByNumberList, @Param("pointByMonth") String pointByMonth);

}
