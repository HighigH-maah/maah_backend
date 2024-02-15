package com.shinhan.maahproject.dto;

import com.shinhan.maahproject.vo.MemberCardHiVO;

import lombok.Data;

@Data
public class ByCardDetailDTO {


	private String memberByNumber;
	private String byName;
	private String byImagePath;
	private String memberCardByNickname;
	private int pointByAmount; //byCard 포인트
	private int byBenefitMinCondition; //byCard 전월실적
	private MemberCardHiVO connectHiCard;
	
	private String benefitName;
	private String byBenefitDesc;
	private String byBenefitBody;
	
}
