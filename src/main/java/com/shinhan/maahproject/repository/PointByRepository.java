package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.PointByMultikey;
import com.shinhan.maahproject.vo.PointByVO;
import java.util.List;


public interface PointByRepository extends CrudRepository<PointByVO, PointByMultikey> {

	List<PointByVO> findByMemberByNumberMonthMemberByNumberAndMemberByNumberMonthPointByMonth(String memberByNumber, String pointByMonth);
}
