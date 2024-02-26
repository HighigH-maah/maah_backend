package com.shinhan.maahproject.dto;

import java.util.List;

import com.shinhan.maahproject.vo.MemberCardHiVO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ByCardDetailDTO {

	public ByCardDetailDTO(String memberByNumber, int byCode, String byName, String memberCardByNickname, String byImagePath,
			int pointByAmount, String pointByMonth, int byBenefitMinCondition, String connectHiCard, int memberByPointGoal, int memberByRank) {
		
		this.memberByNumber = memberByNumber;
		this.byCode = byCode;
		this.byName = byName;
		this.memberCardByNickname = memberCardByNickname;
		this.byImagePath = byImagePath;
		this.pointByMonth = pointByMonth;
		this.pointByAmount = pointByAmount;
		this.byBenefitMinCondition = byBenefitMinCondition;
		this.connectHiCard = connectHiCard;
		this.memberByPointGoal = memberByPointGoal;
		this.memberByRank = memberByRank;
	}
	
	
	
	private List<String> byCategoryList;
	private List<String> benefitList;
	private String memberByNumber;
	private int byCode;
	private String byName;
	private String memberCardByNickname;
	private String byImagePath;
	private String pointByMonth; //byCard pointmonth
	private int pointByAmount; //byCard 포인트
	private int byBenefitMinCondition; //byCard 전월실적
	private int byMinLimit;
	private boolean byIsTransport;
	private String connectHiCard;
	private int memberByPointGoal;
	private int memberByRank;
	
}

