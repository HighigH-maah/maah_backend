package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.MemberAccountMultikey;
import com.shinhan.maahproject.vo.MemberAccountVO;
import com.shinhan.maahproject.vo.MemberVO;

import java.util.List;
import java.util.Optional;


public interface MemberAccountRepository extends CrudRepository<MemberAccountVO, MemberAccountMultikey>{
	
	
}
