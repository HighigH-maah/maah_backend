package com.shinhan.maahproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;

import java.util.List;


public interface MemberCardHiRepository extends JpaRepository<MemberCardHiVO, String> {
	MemberCardHiVO findByMemberHiOwner(String memberHiOwner);

}
