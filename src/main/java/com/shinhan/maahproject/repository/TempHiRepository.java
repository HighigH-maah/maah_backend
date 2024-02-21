package com.shinhan.maahproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.TempHiVO;
import java.util.List;

public interface TempHiRepository extends JpaRepository<TempHiVO, String> { 
	//TempHiVO findByMemberCardHiAndTempHiStatus(MemberCardHiVO memberCardHi, int tempHiStatus);

	 List<TempHiVO> findByMemberCardHiAndTempHiStatus(MemberCardHiVO memberCardHi, int tempHiStatus);
	 
	 List<TempHiVO> findByMemberCardHi(MemberCardHiVO memberCardHi);
}
