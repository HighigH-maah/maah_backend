package com.shinhan.maahproject.repository;
 
import org.springframework.data.repository.CrudRepository;
import com.shinhan.maahproject.vo.BankVO;
 
public interface BankRepository extends CrudRepository<BankVO, String>{
	
}
