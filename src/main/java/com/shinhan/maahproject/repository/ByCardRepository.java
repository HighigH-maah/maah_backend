package com.shinhan.maahproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.maahproject.vo.ByCardVO;
import java.util.List;


public interface ByCardRepository extends CrudRepository<ByCardVO, Integer> {

	List<ByCardVO> findByByCode(int byCode);
	
}
