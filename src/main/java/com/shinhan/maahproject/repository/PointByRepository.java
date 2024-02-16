package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.PointByMultikey;
import com.shinhan.maahproject.vo.PointByVO;
import java.util.List;
import com.shinhan.maahproject.vo.MemberCardByVO;




public interface PointByRepository extends CrudRepository<PointByVO, PointByMultikey> {


	List<PointByVO> findByMemberByNumber(MemberCardByVO memberByNumber);
	
	List<PointByVO> findByMemberByNumberAndPointByMonth(MemberCardByVO memberByNumber, String pointByMonth);

//	List<PointByVO> findByMemberByNumberMonthMemberByNumberAndMemberByNumberMonthPointByMonth(String memberByNumber, String pointByMonth);
}
