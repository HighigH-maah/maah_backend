package com.shinhan.maahproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.vo.MemberVO;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberTest {

	@Autowired
	MemberRepository mRepo;
	
	@Transactional//서비스 만들어서 넣어야 함
	@Test
	void MemberFind() {

		mRepo.findAll().forEach((member) -> {
			log.info(member.getClass_benefit().toString());
			log.info(member.toString());

		});
	}

}
