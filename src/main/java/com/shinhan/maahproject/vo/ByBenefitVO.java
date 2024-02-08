package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode //모든 칼럼을 비교하여 내용 같아야 함
@Getter
@Setter
@ToString(exclude = {"BenefitWayCategoryVO", "BenefitCategoryVO", "byBenefitApplyCategory"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "by_benefit")
public class ByBenefitVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int byBenefitCode;
	
	@NonNull
	@ManyToOne
	@JoinColumn(name="by_benefit_category_code", nullable = false)
	private BenefitCategoryVO byBenefitCategory;
	
	private int byBenefitMinCondition;
	private Double byBenefitAmount;
	@NonNull
	@ManyToOne
	@JoinColumn(name="by_benefit_apply_category_code" , nullable = false)
	private BenefitApplyCategoryVO byBenefitApplyCategory;
	@NonNull
	@ManyToOne
	@JoinColumn(name="by_benefit_way_category_code", nullable = false)
	private BenefitWayCategoryVO byBenefitWayCategory;
	
	private Integer byBenefitLimitAmount;
	private String byBenefitDesc;
	private String byBenefitBody;
}
