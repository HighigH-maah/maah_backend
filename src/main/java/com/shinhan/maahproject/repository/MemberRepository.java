package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.MemberVO;
import java.util.List;


public interface MemberRepository extends CrudRepository<MemberVO, String> {

	List<MemberVO> findByMemberId(String memberId);
}
