package com.shinhan.maahproject.vo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class ByRelationBenefitMultikey implements Serializable {
	@Column(name="by_relate_benefit_code")
	private Integer byRelateBenefitCode;

	@Column(name="by_card_code")
	private Integer byCardCode;
	
	@Override//card는 이미 안다고 가정, 혜택 코드만 주기
	public String toString() {
	    return byRelateBenefitCode.toString();
	}
}
