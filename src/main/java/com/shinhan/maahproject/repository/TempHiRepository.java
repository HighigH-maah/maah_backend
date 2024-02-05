package com.shinhan.maahproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.TempHiVO;
import java.util.List;

public interface TempHiRepository extends JpaRepository<TempHiVO, String> { 
	//TempHiVO findByMember_card_hiAndfindByTemp_hi_status(String member_card_hi, int temp_hi_status);
	
	TempHiVO findBymember_card_hiAndtemp_hi_status(MemberCardHiVO member_card_hi, int temp_hi_status);

}
