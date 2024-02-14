package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.CardHistoryVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;

import java.util.List;


public interface CardHistoryRepository extends CrudRepository<CardHistoryVO, Long> {
	List<CardHistoryVO> findByMemberCardHi(MemberCardHiVO memberCardHi);
	 List<CardHistoryVO> findByMemberCardHiMemberHiNumber(String memberHiNumber);

}
