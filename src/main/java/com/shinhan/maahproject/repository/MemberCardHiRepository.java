package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;

import java.util.List;


public interface MemberCardHiRepository extends CrudRepository<MemberCardHiVO, String> {
	//MemberCardHiVO findByMemberHiOwner(MemberVO memberHiOwner);

	List<MemberCardHiVO> findByMemberHiOwnerAndMemberHiStatus(MemberVO memberHiOwner, int memberHiStatus);
}
