package com.shinhan.maahproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BenefitDTO {
	private String byBenefitDesc;
	private int benefitCode;
}
