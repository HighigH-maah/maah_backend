package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.MemberBenefitVO;
import com.shinhan.maahproject.vo.MemberVO;

import java.util.List;


public interface MemberBenefitRepository extends CrudRepository<MemberBenefitVO, Long>{

	List<MemberBenefitVO> findByMemberBenefitMemberId(MemberVO memberBenefitMemberId);
}
