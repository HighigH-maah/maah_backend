package com.shinhan.maahproject.dto;

import lombok.Data;

@Data
public class HiCardDetailDTO {
	
	private String hiCardImageFrontPath;
	private String memberHiNickname;
	private int memberMileage;
	private String classBenefitName;
	private Integer cardApplyLimitAmount;
}
