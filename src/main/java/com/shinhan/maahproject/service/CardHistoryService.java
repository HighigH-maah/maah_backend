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

import com.shinhan.maahproject.dto.ByCardDetailDTO;
import com.shinhan.maahproject.dto.CategoryBenefitDTO;
import com.shinhan.maahproject.dto.MemberDTO;
import com.shinhan.maahproject.dto.MyDataLimitDTO;
import com.shinhan.maahproject.dto.MyNextLevelDTO;
import com.shinhan.maahproject.repository.BenefitCategoryRepository;
import com.shinhan.maahproject.repository.CardHistoryRepository;
import com.shinhan.maahproject.repository.ClassBenefitRepository;
import com.shinhan.maahproject.repository.MemberAccountRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.vo.BenefitCategoryVO;
import com.shinhan.maahproject.vo.CardHistoryVO;
import com.shinhan.maahproject.vo.ClassBenefitVO;
import com.shinhan.maahproject.vo.MemberAccountVO;
import com.shinhan.maahproject.vo.MemberCardByVO;
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

	@Autowired
	MemberCardByRepository mcbRepo;

	// Hi와 By 한도 현황 및 남은 금액
	public MyDataLimitDTO getHistory(String memberHiNumber, Map<Integer, List<ByCardDetailDTO>> byCardInfo) {
		Long historyAmount = 0L; // actual usage (Hi+By)
		Long limitedAmount = 0L; // card limit (By들의 한도)

		MyDataLimitDTO myLimit = new MyDataLimitDTO();

		List<CardHistoryVO> chList = chRepo.findByMemberCardHiMemberHiNumber(memberHiNumber);
		LocalDate currentDate = LocalDate.now();

		// 바이카드 사용 이력
		for (Map.Entry<Integer, List<ByCardDetailDTO>> entry : byCardInfo.entrySet()) {
			for (ByCardDetailDTO cardDetailDTO : entry.getValue()) {
				String ByNumber = cardDetailDTO.getMemberByNumber(); // 바이카드 넘버를 가져옴
				MemberCardByVO mbvo = mcbRepo.findById(ByNumber).orElse(null); // 멤버카드VO 가져옴
				List<CardHistoryVO> chByHistory = chRepo.findByMemberCardhistoryB(ByNumber); // 이제 history에 접근
				// 각각의 바이카드 한도 누적
				limitedAmount += mbvo.getMemberByLimit();
				// 바이카드 이용 내역 축적
				historyAmount += CalculateAmountBySamePeriod(currentDate, chByHistory);
			}
		}
		// 하이카드 이용 내역 축적
		historyAmount += CalculateAmountBySamePeriod(currentDate, chList);
		myLimit.setHistoryAmount(historyAmount);
		myLimit.setLimitedAmount(limitedAmount);
		return myLimit;
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

	// Hi & By 저번달 사용 내역
	public Long getLastMonthHistory(String memberHiNumber, Map<Integer, List<ByCardDetailDTO>> byCardInfo) {
		Long historyAmount = 0L; // actual usage
		LocalDate currentDate = LocalDate.now();
		// 바이카드 이용 내역 축적
		for (Map.Entry<Integer, List<ByCardDetailDTO>> entry : byCardInfo.entrySet()) {
			for (ByCardDetailDTO cardDetailDTO : entry.getValue()) {
				String ByNumber = cardDetailDTO.getMemberByNumber(); // 바이카드 넘버를 가져옴
				MemberCardByVO mbvo = mcbRepo.findById(ByNumber).orElse(null); // 멤버카드VO 가져옴
				List<CardHistoryVO> chByHistory = chRepo.findByMemberCardhistoryB(ByNumber); // 이제 history에 접근
				historyAmount += CalculateAmountByPrevious(currentDate, chByHistory);
			}
		}

		// 하이카드 이용 내역 축적
		List<CardHistoryVO> chList = chRepo.findByMemberCardHiMemberHiNumber(memberHiNumber);
		historyAmount += CalculateAmountByPrevious(currentDate, chList);
		return historyAmount;
	}

	// 같은 년도, 같은 달 이용 내역 축적
	public Long CalculateAmountBySamePeriod(LocalDate currentDate, List<CardHistoryVO> history) {
		Long totalAmount = 0L;
		for (CardHistoryVO amount : history) {
			LocalDate cardHistoryDate = convertTimestampToLocalDate(amount.getCardHistoryDate());
			if (isWithinCurrentMonth(currentDate, cardHistoryDate)) {
				totalAmount += amount.getCardHistoryAmount();
			}
		}
		return totalAmount;
	}

	// 같은 년도, 전 달 이용 내역 축적
	public Long CalculateAmountByPrevious(LocalDate currentDate, List<CardHistoryVO> history) {
		Long totalAmount = 0L;
		for (CardHistoryVO amount : history) {
			LocalDate cardHistoryDate = convertTimestampToLocalDate(amount.getCardHistoryDate());
			if (isSameYearAndPreviousMonth(currentDate, cardHistoryDate)) {
				totalAmount += amount.getCardHistoryAmount();
			}
		}
		return totalAmount;
	}

	// 카테고리 비율 계산
	public List<CategoryBenefitDTO> getCategoryView(String memberHiNumber,
			Map<Integer, List<ByCardDetailDTO>> byCardInfo) {
		Iterable<BenefitCategoryVO> bcList = bcRepo.findAll();
		Map<Integer, Long> usageByCategory = new HashMap<>();
		LocalDate currentDate = LocalDate.now();

		for (Map.Entry<Integer, List<ByCardDetailDTO>> entry : byCardInfo.entrySet()) {
			for (ByCardDetailDTO cardDetailDTO : entry.getValue()) {
				String ByNumber = cardDetailDTO.getMemberByNumber(); // 바이카드 넘버를 가져옴
				MemberCardByVO mbvo = mcbRepo.findById(ByNumber).orElse(null); // 멤버카드VO 가져옴
				List<CardHistoryVO> chByHistory = chRepo.findByMemberCardhistoryB(ByNumber); // 이제 history에 접근
				// 바이카드 카테고리 누적
				usageByCategory = calculateCategory(bcList,currentDate,chByHistory,usageByCategory);
			}
		}

	
		// 하이카드 카테고리 누적
		List<CardHistoryVO> chList = chRepo.findByMemberCardHiMemberHiNumber(memberHiNumber);
		usageByCategory = calculateCategory(bcList,currentDate,chList,usageByCategory);
	
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
		return categoryBenefitList;
	}

	// 카테고리 계산 맵 로직
	public Map<Integer, Long> calculateCategory(Iterable<BenefitCategoryVO> bcList, LocalDate currentDate,
			List<CardHistoryVO> chHistory, Map<Integer, Long> usageByCategory) {
		for (CardHistoryVO cardHistory : chHistory) {
			LocalDate cardHistoryDate = convertTimestampToLocalDate(cardHistory.getCardHistoryDate());
			if (isWithinCurrentMonth(currentDate, cardHistoryDate)) {
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

		return usageByCategory;
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
