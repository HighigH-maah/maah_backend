package com.shinhan.maahproject.dto;

import com.shinhan.maahproject.vo.HiCardImageVO;

import lombok.Data;

@Data
public class MyCardHiDTO {
	private String memberHiNumber;
	private String memberHiNickname;
	private HiCardImageVO hiImageCode;
	private Integer pointHiAmount;
	private String classBenefitName;
	private Integer thismonthsum;
	private Integer totalLimit;
}
