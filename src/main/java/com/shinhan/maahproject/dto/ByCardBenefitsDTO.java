package com.shinhan.maahproject.dto;

import lombok.Data;

@Data
public class ByCardBenefitsDTO {
	private int byCode;
	private String benefitName;
	private String byBenefitDesc;
	private String byBenefitBody;
	private int byBenefitMinCondition;
	
	//생성자
    public ByCardBenefitsDTO(int byCode, String benefitName, String byBenefitDesc, String byBenefitBody, int byBenefitMinCondition) {
    	this.byCode = byCode;
        this.benefitName = benefitName;
        this.byBenefitDesc = byBenefitDesc;
        this.byBenefitBody = byBenefitBody;
        this.byBenefitMinCondition = byBenefitMinCondition;
    }
}
