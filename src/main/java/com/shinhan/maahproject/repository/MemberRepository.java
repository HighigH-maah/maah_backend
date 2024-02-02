package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.MemberVO;

public interface MemberRepository extends CrudRepository<MemberVO, String> {

}
