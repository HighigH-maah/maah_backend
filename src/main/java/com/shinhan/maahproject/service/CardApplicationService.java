package com.shinhan.maahproject.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.CardApplyDTO;
import com.shinhan.maahproject.dto.MyCardByDTO;
import com.shinhan.maahproject.repository.BankRepository;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.repository.CardApplyRepository;
import com.shinhan.maahproject.repository.HiCardImageRepository;
import com.shinhan.maahproject.repository.MemberAccountRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.vo.BankVO;
import com.shinhan.maahproject.vo.ByCardVO;
import com.shinhan.maahproject.vo.CardApplyVO;
import com.shinhan.maahproject.vo.HiCardImageVO;
import com.shinhan.maahproject.vo.MemberAccountMultikey;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;

@Service
public class CardApplicationService {
	
	@Autowired
	MyCardListService mclService;
	
	@Autowired
	HiCardImageRepository hicardImgRepo;
	
	@Autowired
	MemberCardHiRepository hicardRepo;
	
	@Autowired
	MemberCardByRepository memberByRepo;
	
	@Autowired
	ByCardRepository bycardRepo;
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	MemberAccountRepository accountRepo;
	
	@Autowired
	BankRepository bankRepo;
	
	@Autowired
	CardApplyRepository applyRepo;

	public Map<String, Object> getHicardDesigns(String userId) {
		Map<String, Object> res = new HashMap<>();
		List<Map<String, String>> by = new ArrayList<>();
		List<String> img = new ArrayList<>();
		
		for(MyCardByDTO my : mclService.getMyCardListBy(userId)) {
			Map<String, String> myCard = new HashMap<>();
			myCard.put("nickname", my.getMemberCardByNickname());
			myCard.put("image", my.getByImagePath());
			img.add(my.getByImagePath());
			by.add(myCard);
		}
		
		int length = by.size() % 5 == 0 ? 0 : 5 - (by.size() % 5);
		length = by.size() == 0 ? 5 : length;
		if(length > 0) {
			List<ByCardVO> list = (List<ByCardVO>) bycardRepo.findAll();
			for(int i = 0; i < length; i++) {
				while(true) {
					int num = (int) (Math.random() * list.size());
					if(!img.contains(list.get(num).getByImagePath())) {
						Map<String, String> myCard = new HashMap<>();
						myCard.put("nickname", list.get(num).getByName());
						myCard.put("image", list.get(num).getByImagePath());
						img.add(myCard.get("image"));
						by.add(myCard);
						break;
					}
				}
			}
		}
		
		res.put("hi", hicardImgRepo.findAll());
		res.put("by", by);
		
		return res;
	}

	@SuppressWarnings("null")
	public HiCardImageVO applyCard(CardApplyDTO cardApply) {
		CardApplyVO applyInfo = changeDTO(cardApply);
		applyRepo.save(applyInfo);
		if(cardApply.getType().equals("hi")) {
			
			// 만료일 계산
			Calendar cal = Calendar.getInstance();
			Timestamp expdate = (Timestamp) applyInfo.getCardApplyDate().clone();
			cal.setTime(expdate);
			cal.add(Calendar.YEAR, 5);
			expdate.setTime(cal.getTime().getTime());
			
			// 카드번호 생성
			boolean isNum = true;
			String cardNumber = "";
			while(isNum) {
				cardNumber = Integer.toString((int) (Math.random() * 9999999));
				for(int i = 0; i < 7 - cardNumber.length(); i++) {
					cardNumber = "0" + cardNumber;
				}
				cardNumber = "1" + cardNumber;
				isNum = !hicardRepo.findById(cardNumber).isEmpty();
			}
			
			// CVC 생성
			String cvc = Integer.toString((int) (Math.random() * 999));
			for(int i = 0; i < 3 - cvc.length(); i++) {
				cvc = "0" + cvc;
			}
			
			HiCardImageVO imgCode = hicardImgRepo.findById(Integer.parseInt(cardApply.getCard())).get();
			MemberCardHiVO card = MemberCardHiVO.builder()
					.memberHiNumber(cardNumber)
					.memberHiPassword(applyInfo.getCardApplyPassword())
					.memberHiOwner(applyInfo.getMember())
					.memberAccountKey(applyInfo.getMemberAccountKey())
					.hiImageCode(imgCode)
					.memberHiRegdate(applyInfo.getCardApplyDate())
					.memberHiExpdate(expdate)
					.memberHiPaydate(applyInfo.getCardApplyPaydate())
					.memberHiCvc(cvc)
					.cardApplyCode(applyInfo)
					.memberHiIsTransport(applyInfo.getCardApplyIsTransport())
					.memberHiNickname(applyInfo.getMember().getMemberName() + "님HIcard")
					.build();
			
			hicardRepo.save(card);
			return imgCode;
			
		} else if(cardApply.getType().equals("by")) {
			// 만료일 계산
			Calendar cal = Calendar.getInstance();
			Timestamp expdate = (Timestamp) applyInfo.getCardApplyDate().clone();
			cal.setTime(expdate);
			cal.add(Calendar.YEAR, 5);
			expdate.setTime(cal.getTime().getTime());
			
			// 카드번호 생성
			boolean isNum = true;
			String cardNumber = "";
			while(isNum) {
				cardNumber = Integer.toString((int) (Math.random() * 9999999));
				for(int i = 0; i < 7 - cardNumber.length(); i++) {
					cardNumber = "0" + cardNumber;
				}
				cardNumber = (2 + (Integer.parseInt(cardApply.getCard()) % 8)) + cardNumber;
				isNum = !memberByRepo.findById(cardNumber).isEmpty();
			}
			
			// CVC 생성
			String cvc = Integer.toString((int) (Math.random() * 999));
			for(int i = 0; i < 3 - cvc.length(); i++) {
				cvc = "0" + cvc;
			}
			
			MemberCardByVO card = MemberCardByVO.builder()
					.memberByNumber(cardNumber)
					.memberByPassword(applyInfo.getCardApplyPassword())
					.memberByLimit(cardApply.getCardApplyLimitAmount() * 10000)
					.member(applyInfo.getMember())
					.memberAccountKey(applyInfo.getMemberAccountKey())
					.memberByStatus(0)
					.byCard(bycardRepo.findById(Integer.parseInt(cardApply.getCard())).get())
					.memberByRegdate(applyInfo.getCardApplyDate())
					.memberByExpdate(expdate)
					.memberByPaydate(applyInfo.getCardApplyPaydate())
					.memberByCvc(cvc)
					.applyCode(applyInfo)
					.memberByRank(10)
					.memberByIsTransport(applyInfo.getCardApplyIsTransport())
					.memberCardByNickname(applyInfo.getMember().getMemberName() + "님" + bycardRepo.findById(Integer.parseInt(cardApply.getCard())).get().getByName())
					.build();
			
			memberByRepo.save(card);
			HiCardImageVO cardImg = HiCardImageVO.builder()
					.hiCardImageFrontPath(bycardRepo.findById(Integer.parseInt(cardApply.getCard())).get().getByImagePath())
					.hiCardImageName(bycardRepo.findById(Integer.parseInt(cardApply.getCard())).get().getByName())
					.build();
			return cardImg;
		} else {
			return null;
		}
	}
	
	public List<BankVO> getCardApplyBankCode() {
		return (List<BankVO>) bankRepo.findAll();
	}
	
	private CardApplyVO changeDTO(CardApplyDTO cardApply) {
		return CardApplyVO.builder()
				.member(memberRepo.findById(cardApply.getMemberId()).get())
				.cardApplyMemberSocialNumber(cardApply.getCardApplyMemberSocialNumber())
				.cardApplyDate(cardApply.getCardApplyDate())
				.cardApplyIdIssueDate(cardApply.getCardApplyIdIssueDate())
				.cardApplyIsTermsOfService(cardApply.getCardApplyIsTermsOfService())
				.cardApplyAnnualIncome(cardApply.getCardApplyAnnualIncome())
				.cardApplyPaydate(cardApply.getCardApplyPaydate())
				.cardApplyCreditPoint(cardApply.getCardApplyCreditPoint())
				.cardApplySourceFund(cardApply.getCardApplySourceFund())
				.cardApplyPurpose(cardApply.getCardApplyPurpose())
				.cardApplyIsVerify(cardApply.getCardApplyIsVerify())
				.cardApplyEngname(cardApply.getCardApplyEngname())
				.cardApplyIsInternational(cardApply.getCardApplyIsInternational())
				.cardApplyIsAccountVerify(cardApply.getCardApplyIsAccountVerify())
				.cardApplyLimitAmount(cardApply.getCardApplyLimitAmount())
				.cardApplyAddress(cardApply.getCardApplyAddress())
				.cardApplyPassword(cardApply.getCardApplyPassword())
				.cardApplyIsTransport(cardApply.getCardApplyIsTransport())
				.memberAccountKey(accountRepo.findById(MemberAccountMultikey.builder()
						.memberAccountNumber(cardApply.getAccountNumber())
						.bank(cardApply.getBankCode()).build()).get())
				.build();
	}

}
