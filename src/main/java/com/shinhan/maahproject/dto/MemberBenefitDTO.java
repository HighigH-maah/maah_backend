package com.shinhan.maahproject.dto;

import com.shinhan.maahproject.vo.ByBenefitVO;

import lombok.Data;

@Data
public class MemberBenefitDTO {
	private int benefitCode;
	private String byBenefitDesc;
	private int memberBenefitUsedAmount;
	private Boolean memberBenefitIsComplete;
	private Integer memberBenefitPriorityRange;
}
