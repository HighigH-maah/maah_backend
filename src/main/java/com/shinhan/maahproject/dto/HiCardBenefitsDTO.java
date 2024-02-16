package com.shinhan.maahproject.dto;

import lombok.Data;

@Data
public class HiCardBenefitsDTO {
	
	private String benefitName;
    private String byBenefitDesc;
    private String byBenefitBody;
    
    //생성자
    public HiCardBenefitsDTO(String benefitName, String byBenefitDesc, String byBenefitBody) {
        this.benefitName = benefitName;
        this.byBenefitDesc = byBenefitDesc;
        this.byBenefitBody = byBenefitBody;
    }
}
