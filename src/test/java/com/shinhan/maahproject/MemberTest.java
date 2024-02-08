package com.shinhan.maahproject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
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
import com.shinhan.maahproject.utils.S3Config;
import com.shinhan.maahproject.utils.S3Upload;
import com.shinhan.maahproject.vo.MemberBenefitVO;
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
	S3Upload uploadService;
	
	@Test
	void uploadTest() throws SdkClientException, IOException {
		UUID randomUUID = UUID.randomUUID();
		String filePath = "C:\\Users\\User\\Desktop\\image\\card3.png";
		Path path = Paths.get(filePath);
		
		String fileName = path.getFileName().toString();
		System.out.println(fileName);
		String contentType = Files.probeContentType(path);
		byte[] fileContent = Files.readAllBytes(path);
		MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, contentType, fileContent);
		
		
		//uploadService.upload(multipartFile, filePath, randomUUID.toString());
	}
	
	//@Test
	void OtherCardFind() {
		oRepo.findAll().forEach((ph) -> {
			log.info(ph.toString());
		});
	}
	
	
	//@Test
	@Transactional
	void MemberBenefitFind() {
		bmRepo.findAll().forEach((ph) -> {
			log.info(ph.toString());
		});
		
	}
	

	//@Test
	@Transactional
	void ByCardFind() {
		mbRepo.findAll().forEach((ph) -> {
			log.info(ph.toString());
		});
		
	}

	//@Test
	@Transactional
	void HiCardFind() {

		mhRepo.findAll().forEach((hi) -> {
			log.info(hi.toString());
		});
	}

	// @Test
	void StoreFind() {
		mcRepo.findAll().forEach((mc) -> {
			log.info(mc.toString());
		});

		// sRepo.save(StoreCouponVO.builder().store_name("푸하하크림빵").build());
		sRepo.findAll().forEach((store) -> {
			log.info(store.toString());
		});
	}

	// @Test
	void CardApplyFind() {
		cRepo.findAll().forEach((apply) -> {
			log.info(apply.toString());
		});
	}

	@Transactional // 서비스 만들어서 넣어야 함
	//@Test
	void MemberFind() {
		mRepo.findAll().forEach((member) -> {
			log.info(member.getClassBenefit().toString());
			log.info(member.toString());
		});
	}

}
