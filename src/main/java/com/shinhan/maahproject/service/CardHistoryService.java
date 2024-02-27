package com.shinhan.maahproject.service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.dto.ByCardDetailDTO;
import com.shinhan.maahproject.dto.CategoryBenefitDTO;
import com.shinhan.maahproject.dto.MyDataCompareDTO;
import com.shinhan.maahproject.dto.MyDataLimitDTO;
import com.shinhan.maahproject.dto.MyNextLevelDTO;
import com.shinhan.maahproject.dto.myDataCardForMonthDTO;
import com.shinhan.maahproject.repository.BenefitCategoryRepository;
import com.shinhan.maahproject.repository.ByCardRepository;
import com.shinhan.maahproject.repository.CardHistoryRepository;
import com.shinhan.maahproject.repository.ClassBenefitRepository;
import com.shinhan.maahproject.repository.MemberAccountRepository;
import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.PointByRepository;
import com.shinhan.maahproject.vo.BenefitCategoryVO;
import com.shinhan.maahproject.vo.CardHistoryVO;
import com.shinhan.maahproject.vo.ClassBenefitVO;
import com.shinhan.maahproject.vo.MemberAccountVO;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberVO;
import com.shinhan.maahproject.vo.PointByVO;

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
	
	@Autowired
	ByCardRepository bRepo;

	@Autowired
	PointByRepository pbRepo;

	// By 카드 포인트 현황
	public List<myDataCardForMonthDTO> cardForMonth(Map<Integer, List<ByCardDetailDTO>> byCardInfo) {
		List<PointByVO> pointList = new ArrayList<>();
		List<myDataCardForMonthDTO> cardForMonth = new ArrayList<>();// DTO 선언

		for (Map.Entry<Integer, List<ByCardDetailDTO>> entry : byCardInfo.entrySet()) {
			for (ByCardDetailDTO cardDetailDTO : entry.getValue()) {
				String ByNumber = cardDetailDTO.getMemberByNumber(); // 바이카드 넘버를 가져옴
				MemberCardByVO mbvo = mcbRepo.findById(ByNumber).orElse(null); // 멤버카드VO 가져옴
				// 이번 달 찾기
				myDataCardForMonthDTO cardFor = new myDataCardForMonthDTO();
				String currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
				pointList = pbRepo.findByMemberByNumberAndPointByMonth(mbvo, currentYearMonth);
				// 없는 포인트 제외, 점수 넣어주기
				if (!pointList.isEmpty()) {
					cardFor.setPoint(pointList.get(0).getPointByAmount());
				}
				cardFor.setByCardName(mbvo.getMemberCardByNickname());
				cardFor.setByCardImage(mbvo.getByCard().getByImagePath());
				cardForMonth.add(cardFor);
			}
		}
		return cardForMonth;
	}

	// 저번달 VS 이번달
	public MyDataCompareDTO getCompare(String memberHiNumber, Long diff) {
	    Long firstSumCurrentMonth = 0L;
	    Long middleSumCurrentMonth = 0L;
	    Long lastSumCurrentMonth = 0L;
	    Long moreThanUsedSumCurrentMonth = 0L;

	    Long firstSumPreviousMonth = 0L;
	    Long middleSumPreviousMonth = 0L;
	    Long lastSumPreviousMonth = 0L;
	    Long moreThanUsedSumPreviousMonth = 0L;

	    LocalDateTime now = LocalDateTime.now();
	    LocalDateTime firstDayCurrentMonth = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0);
	    LocalDateTime firstDayPreviousMonth = firstDayCurrentMonth.minusMonths(1);

	    List<CardHistoryVO> chList = chRepo.findByMemberCardHiMemberHiNumber(memberHiNumber);

	    for (CardHistoryVO amount : chList) {
	        LocalDateTime historyDate = amount.getCardHistoryDate().toLocalDateTime();
	        Long amountValue = (long) amount.getCardHistoryAmount();

	        if (historyDate.isAfter(firstDayCurrentMonth)) {
	            // 이번 달
	            if (historyDate.getDayOfMonth() == now.getDayOfMonth()) {
	                firstSumCurrentMonth += amountValue;
	            }
	            if (historyDate.isAfter(now.minusDays(15))) {
	                middleSumCurrentMonth += amountValue;
	            }
	            if (historyDate.isAfter(now.minusDays(30))) {
	                lastSumCurrentMonth += amountValue;
	            }
	            moreThanUsedSumCurrentMonth += amountValue;
	        } else if (historyDate.isAfter(firstDayPreviousMonth) && historyDate.isBefore(firstDayCurrentMonth)) {
	            // 전달
	        	
	            if (historyDate.getDayOfMonth() == 1) {
	                firstSumPreviousMonth += amountValue;
	            }
	            if (historyDate.getDayOfMonth() == 15) {
	                middleSumPreviousMonth += amountValue;
	            }
	            if (historyDate.getDayOfMonth() == 30 || historyDate.getDayOfMonth() == historyDate.toLocalDate().lengthOfMonth()) {
	                lastSumPreviousMonth += amountValue;
	            }
	            moreThanUsedSumPreviousMonth += amountValue;
	        }
	    }


	    MyDataCompareDTO myCompare = new MyDataCompareDTO();
	    myCompare.setFirst(firstSumCurrentMonth);
	    myCompare.setMiddle(middleSumCurrentMonth);
	    myCompare.setLast(lastSumCurrentMonth);
	    myCompare.setMoreThanUsed(moreThanUsedSumCurrentMonth);

	    myCompare.setPfirst(firstSumPreviousMonth);
	    myCompare.setPmiddle(middleSumPreviousMonth);
	    myCompare.setPlast(lastSumPreviousMonth);
	    myCompare.setMoreThanUsed(diff); //?

	    return myCompare;
	}

	public long getMonthAvg(String memberHiNumber) {
		Long historyAmount = 0L;
		List<CardHistoryVO> chList = chRepo.findByMemberCardHiMemberHiNumber(memberHiNumber);
		if (chList.isEmpty()) {
			return 0L;
		}

		Collections.sort(chList, Comparator.comparing(CardHistoryVO::getCardHistoryDate).reversed());
		CardHistoryVO mostRecentItem = chList.get(0);
		CardHistoryVO latestItem = chList.get(chList.size() - 1);

		LocalDateTime mostRecentDateTime = mostRecentItem.getCardHistoryDate().toLocalDateTime();
		LocalDateTime latestDateTime = latestItem.getCardHistoryDate().toLocalDateTime();
		Duration duration = Duration.between(latestDateTime, mostRecentDateTime);
		long daysDifference = duration.toDays();

		if (daysDifference < 30) {
			return 0L;
		}

		long monthsDifference = daysDifference / 30; // 일 수로 나눈다

		// 총 비용 건수를 그 개월로 나눈다
		for (CardHistoryVO amount : chList) {
			historyAmount += amount.getCardHistoryAmount();
		}

		return monthsDifference != 0 ? historyAmount / (monthsDifference+1) : 0L;
	}

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

	// 3개월 이내에 해당하는지에 대한 여부
	private boolean isWithinThreeMonth(LocalDate currentDate, LocalDate dateToCheck) {
	    return ChronoUnit.MONTHS.between(dateToCheck, currentDate) <= 3;
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
	
	// 3개월 간 이용 내역 축적
	public Long CalculateAmountForThreeMonth(LocalDate currentDate, List<CardHistoryVO> history) {
	    Long totalAmount = 0L;
	    for (CardHistoryVO amount : history) {
	        LocalDate cardHistoryDate = convertTimestampToLocalDate(amount.getCardHistoryDate());
	        if (isWithinThreeMonth(currentDate, cardHistoryDate)) {
	            totalAmount += amount.getCardHistoryAmount();
	        }
	    }
	    log.info("totalAmount: " + totalAmount);
	    return totalAmount;
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
				usageByCategory = calculateCategory(bcList, currentDate, chByHistory, usageByCategory);
			}
		}

		// 하이카드 카테고리 누적
		List<CardHistoryVO> chList = chRepo.findByMemberCardHiMemberHiNumber(memberHiNumber);
		usageByCategory = calculateCategory(bcList, currentDate, chList, usageByCategory);

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

	public MyNextLevelDTO tonextLevel(String memId, String memberHiNumber) {
		ModelMapper mapper = new ModelMapper();
		MyNextLevelDTO nextLevel = new MyNextLevelDTO();
		int priceHasUsed = 0; // 3개월동안 HiCard를 사용한 금액의 합
		Optional<MemberVO> optionalMember = mrRepo.findById(memId);
		LocalDate currentDate = LocalDate.now();

		// 하이카드 이용 내역 축적
		List<CardHistoryVO> chList = chRepo.findByMemberCardHiMemberHiNumber(memberHiNumber);
		priceHasUsed += CalculateAmountForThreeMonth(currentDate, chList);
		
		if (optionalMember.isPresent()) {
			MemberVO member = optionalMember.get();
			ClassBenefitVO classBenefit = member.getClassBenefit();

			if (classBenefit != null) {
				int classBenefitCode = classBenefit.getClassBenefitCode();
				String classBenefitName = classBenefit.getClassBenefitName();
				nextLevel.setMemberClass(classBenefitName);
				
				int nextClass = (int) Math.ceil(classBenefitCode + 1);
				Optional<ClassBenefitVO> toNextVO = cbRepo.findById(nextClass);
				nextLevel.setPriceHasUsed(priceHasUsed);
				if (toNextVO.isPresent()) {
					ClassBenefitVO toNext = toNextVO.get();
					int beneRange = toNext.getClassBenefitMinRange();
					nextLevel.setToNextClass(beneRange - priceHasUsed); // 남은 금액
					nextLevel.setPriceHasUsed(priceHasUsed);
					nextLevel.setNextClass(toNext.getClassBenefitName());
				} else { // Platinum인 경우 
					nextLevel.setToNextClass(0); // 남은 금액
					nextLevel.setNextClass(null);
				}
			} else {
				return null;
			}
		} else {
			// 멤버가 없으면
			return null;
		}
		return nextLevel;
	}

}
