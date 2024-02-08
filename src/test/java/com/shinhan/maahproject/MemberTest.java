package com.shinhan.maahproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import com.shinhan.maahproject.dto.AccountChangeDTO;
import com.shinhan.maahproject.repository.BankRepository;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.repository.CardApplyRepository;
import com.shinhan.maahproject.repository.HiCardImageRepository;
import com.shinhan.maahproject.repository.MemberBenefitRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberCouponRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.OtherCardRepository;
import com.shinhan.maahproject.repository.PointHiRepository;
import com.shinhan.maahproject.repository.StoreCouponRepository;
import com.shinhan.maahproject.repository.TempHiRepository;
import com.shinhan.maahproject.vo.MemberBenefitVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberCouponVO;
import com.shinhan.maahproject.vo.MemberVO;
import com.shinhan.maahproject.vo.StoreCouponVO;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberTest {

	@Autowired
	MemberRepository mRepo;

	@Autowired
	CardApplyRepository cRepo;

	@Autowired
	StoreCouponRepository sRepo;

	@Autowired
	MemberCouponRepository mcRepo;

	@Autowired
	HiCardImageRepository hiRepo;

	@Autowired
	MemberCardHiRepository mhRepo;

	@Autowired
	PointHiRepository phRepo;

	@Autowired
	TempHiRepository tRepo;

	@Autowired
	ByCardRepository bcRepo;

	@Autowired
	MemberCardByRepository mbRepo;
	
	@Autowired
	MemberBenefitRepository bmRepo;
	
	@Autowired
	OtherCardRepository oRepo;
	
	@Autowired
	BankRepository bRepo;
	
	@Test
	void BankCodeFind() {
		String memberId = "user3";
		AccountChangeDTO acdto = null;
		MemberCardHiVO tempmhvo = null;

		for (MemberCardHiVO hicard : mhRepo.findByMemberHiOwnerAndMemberHiStatus(mRepo.findById(memberId).orElse(null),
				0)) {
			if (hicard.getMemberHiStatus() == 0) {
				tempmhvo = hicard; //user3의 hicard 정보가 담긴다.
				log.info(tempmhvo.toString());
			}
		}
	}
	
//	@Test
//	void OtherCardFind() {
//		oRepo.findAll().forEach((ph) -> {
//			log.info(ph.toString());
//		});
//	}
//	
//	
//	//@Test
//	@Transactional
//	void MemberBenefitFind() {
//		bmRepo.findAll().forEach((ph) -> {
//			log.info(ph.toString());
//		});
//		
//	}
//	
//
//	//@Test
//	@Transactional
//	void ByCardFind() {
//		mbRepo.findAll().forEach((ph) -> {
//			log.info(ph.toString());
//		});
//		
//	}
//
//	//@Test
//	@Transactional
//	void HiCardFind() {
//
//		mhRepo.findAll().forEach((hi) -> {
//			log.info(hi.toString());
//		});
//	}
//
//	// @Test
//	void StoreFind() {
//		mcRepo.findAll().forEach((mc) -> {
//			log.info(mc.toString());
//		});
//
//		// sRepo.save(StoreCouponVO.builder().store_name("푸하하크림빵").build());
//		sRepo.findAll().forEach((store) -> {
//			log.info(store.toString());
//		});
//	}
//
//	// @Test
//	void CardApplyFind() {
//		cRepo.findAll().forEach((apply) -> {
//			log.info(apply.toString());
//		});
//	}
//
//	@Transactional // 서비스 만들어서 넣어야 함
//	@Test
//	void MemberFind() {
//		mRepo.findAll().forEach((member) -> {
//			log.info(member.getClassBenefit().toString());
//			log.info(member.toString());
//		});
//	}

}
