package com.shinhan.maahproject.dto;

import lombok.Data;

@Data
public class ByCardBenefitsDTO {
	private int byCode;
	private String benefitName;
	private String byBenefitDesc;
	private String byBenefitBody;
	
	//생성자
    public ByCardBenefitsDTO(int byCode, String benefitName, String byBenefitDesc, String byBenefitBody) {
    	this.byCode = byCode;
        this.benefitName = benefitName;
        this.byBenefitDesc = byBenefitDesc;
        this.byBenefitBody = byBenefitBody;
    }

//	public void addBenefit(String benefitName2,String byBenefitDesc, String byBenefitBody) {
//		this.benefitName = benefitName2;
//        this.byBenefitDesc = byBenefitDesc;
//        this.byBenefitBody = byBenefitBody;
//		
//	}
}
