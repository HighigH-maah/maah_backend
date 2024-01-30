package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.MemberAccountMultikey;
import com.shinhan.maahproject.vo.MemberAccountVO;

public interface MemberAccountRepository extends CrudRepository<MemberAccountVO, MemberAccountMultikey>{

}
