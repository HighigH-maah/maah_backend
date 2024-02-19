package com.shinhan.maahproject.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.CategoryBenefitDTO;
import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.MyNextLevelDTO;
import com.shinhan.maahproject.repository.BenefitCategoryRepository;
import com.shinhan.maahproject.repository.CardHistoryRepository;
import com.shinhan.maahproject.repository.ClassBenefitRepository;
import com.shinhan.maahproject.repository.MemberAccountRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.vo.BenefitCategoryVO;
import com.shinhan.maahproject.vo.CardHistoryVO;
import com.shinhan.maahproject.vo.ClassBenefitVO;
import com.shinhan.maahproject.vo.MemberAccountVO;
import com.shinhan.maahproject.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CardHistoryService {

	@Autowired
	CardHistoryRepository chRepo;

	@Autowired
	MemberAccountRepository mRepo;

	@Autowired
	MemberRepository mrRepo;

	@Autowired
	BenefitCategoryRepository bcRepo;

	@Autowired
	ClassBenefitRepository cbRepo;

	public Long getHistory(String memberHiNumber) {
		Long historyAmount = 0L; // actual usage
		Long limitedAmount = 0L; // card limit
		// 하이카드에 대한 결제건만 (바이카드 추가 필요)
		List<CardHistoryVO> chList = chRepo.findByMemberCardHiMemberHiNumber(memberHiNumber);
		LocalDate currentDate = LocalDate.now();
		for (CardHistoryVO amount : chList) {
			log.info("getCardHistoryId : " + amount.getCardHistoryId());
			log.info("amount : " + amount.getCardHistoryAmount());
			LocalDate cardHistoryDate = convertTimestampToLocalDate(amount.getCardHistoryDate());
			if (isWithinCurrentMonth(currentDate, cardHistoryDate)) {
				log.info("셀 수 있는 기간입니다 : " + cardHistoryDate);
				historyAmount += amount.getCardHistoryAmount();
			}
		}
		return historyAmount;
	}

	private LocalDate convertTimestampToLocalDate(Timestamp timestamp) {
		Instant instant = timestamp.toInstant();
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		return dateTime.toLocalDate();
	}

	// 같은 년도와 월에 해당하는지에 대한 여부
	private boolean isWithinCurrentMonth(LocalDate currentDate, LocalDate dateToCheck) {
		return (dateToCheck.getYear() == currentDate.getYear())
				&& (dateToCheck.getMonthValue() == currentDate.getMonthValue())
				&& dateToCheck.isBefore(currentDate.plusDays(1));
	}

	// 같은 년도와 전달에 해당하는지의 여부
	private boolean isSameYearAndPreviousMonth(LocalDate currentDate, LocalDate dateToCheck) {
		if (currentDate.getYear() == dateToCheck.getYear()) {
			return dateToCheck.getMonthValue() == currentDate.minusMonths(1).getMonthValue();
		}
		return false;
	}

	public Long getLastMonthHistory(String memberHiNumber) {
		Long historyAmount = 0L; // actual usage
		LocalDate currentDate = LocalDate.now();
		// 하이카드에 대한 결제건만 (바이카드 추가 필요)
		List<CardHistoryVO> chList = chRepo.findByMemberCardHiMemberHiNumber(memberHiNumber);
		for (CardHistoryVO amount : chList) {
			log.info("getCardHistoryId : " + amount.getCardHistoryId());
			log.info("amount : " + amount.getCardHistoryAmount());
			LocalDate cardHistoryDate = convertTimestampToLocalDate(amount.getCardHistoryDate());
			if (isSameYearAndPreviousMonth(currentDate, cardHistoryDate)) {
				log.info("저번달, 셀 수 있는 기간입니다 : " + cardHistoryDate);
				historyAmount += amount.getCardHistoryAmount();
			}
		}
		return historyAmount;
	}

	// 카테고리 비율 계산
	public List<CategoryBenefitDTO> getCategoryView(String memberHiNumber) {
		// 하이카드에 대한 결제건만 (바이카드 추가 필요)
		List<CardHistoryVO> chList = chRepo.findByMemberCardHiMemberHiNumber(memberHiNumber);
		Iterable<BenefitCategoryVO> bcList = bcRepo.findAll();
		Map<Integer, Long> usageByCategory = new HashMap<>();
		LocalDate currentDate = LocalDate.now();

		for (CardHistoryVO cardHistory : chList) {
			LocalDate cardHistoryDate = convertTimestampToLocalDate(cardHistory.getCardHistoryDate());
			log.info("cardHistoryDate" + cardHistoryDate);

			// 이번 달에 해당하는 데이터만 계산
			if (isWithinCurrentMonth(currentDate, cardHistoryDate)) {
				log.info("In it" + cardHistoryDate);
				int cateHistoryCategory = cardHistory.getCardHistoryCategory(); // Assuming this method exists
				long cardHistoryAmount = cardHistory.getCardHistoryAmount();

				for (BenefitCategoryVO benefitCategory : bcList) {
					int benefitCode = benefitCategory.getBenefitCode();

					if (cateHistoryCategory == benefitCode) {
						usageByCategory.put(benefitCode,
								usageByCategory.getOrDefault(benefitCode, 0L) + cardHistoryAmount);
						break;
					}
				}
			}
		}

		// CategoryBenefitDTO에 항목별로 금액 담기
		List<CategoryBenefitDTO> categoryBenefitList = new ArrayList();
		for (BenefitCategoryVO benefitCategory : bcList) {
			int benefitCode = benefitCategory.getBenefitCode();
			String benefitName = benefitCategory.getBenefitName();
			Long totalUsageAmount = usageByCategory.getOrDefault(benefitCode, 0L);

			CategoryBenefitDTO categoryBenefitDTO = new CategoryBenefitDTO();
			categoryBenefitDTO.setBenefitName(benefitName);
			categoryBenefitDTO.setBenefitData(totalUsageAmount);
			categoryBenefitList.add(categoryBenefitDTO);
		}

		for (CategoryBenefitDTO categoryBenefitDTO : categoryBenefitList) {
			log.info("Benefit Name: " + categoryBenefitDTO.getBenefitName() + ", Total Usage Amount: "
					+ categoryBenefitDTO.getBenefitData());
		}

		return categoryBenefitList;
	}

	public List<MemberAccountVO> getAccount() {
		log.info(mRepo.findByMemberAccountMemberIdMemberId("user2").toString());
		List<MemberAccountVO> mAccountList = mRepo.findByMemberAccountMemberIdMemberId("user2");
		return mRepo.findByMemberAccountMemberIdMemberId("user2");
	}

	public MyNextLevelDTO tonextLevel(String memId) {
		ModelMapper mapper = new ModelMapper();
		MyNextLevelDTO nextLevel = new MyNextLevelDTO();
		Optional<MemberVO> optionalMember = mrRepo.findById(memId);

		if (optionalMember.isPresent()) {
			MemberVO member = optionalMember.get();
			ClassBenefitVO classBenefit = member.getClassBenefit();

			if (classBenefit != null) {
				int classBenefitCode = classBenefit.getClassBenefitCode();
				String classBenefitName = classBenefit.getClassBenefitName();
				nextLevel.setMemberClass(classBenefitName);

				int priceHasUsed = 1000; // 현재까지 사용한 금액 로직 추가 필요

				int nextClass = (int) Math.ceil(classBenefitCode + 1);
				Optional<ClassBenefitVO> toNextVO = cbRepo.findById(nextClass);
				if (toNextVO.isPresent()) {
					ClassBenefitVO toNext = toNextVO.get();
					int beneRange = toNext.getClassBenefitMinRange();
					nextLevel.setToNextClass(beneRange - priceHasUsed); // 남은 금액
					nextLevel.setNextClass(toNext.getClassBenefitName());
				}
			} else {
				// Handle the case when ClassBenefitVO is null
				return null;
			}
		} else {
			// 멤버가 없으면
			return null;
		}
		return nextLevel;
	}

}
