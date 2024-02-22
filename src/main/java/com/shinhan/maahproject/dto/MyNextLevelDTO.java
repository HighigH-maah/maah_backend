package com.shinhan.maahproject.dto;

import com.shinhan.maahproject.vo.BenefitCategoryVO;

import lombok.Data;

@Data
public class MyNextLevelDTO {
	private String memberClass; // 현재 레벨
	private int toNextClass; // 다음 레벨까지 남은 금액
	private String nextClass; // 다음 레벨
	private int priceHasUsed; // 사용한 금액
	
}
