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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "by_benefit")
public class ByBenefitVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int by_benefit_code;
	@NonNull
	@Column(nullable = false)
	@ManyToOne
	@JoinColumn(name="by_benefit_category_code")
	private BenefitCategoryVO by_benefit_category;
	
	private int by_benefit_min_condition;
	private int by_benefit_amount;
	@NonNull
	@Column(nullable = false)
	@ManyToOne
	@JoinColumn(name="by_benefit_apply_category_code")
	private BenefitApplyCategoryVO by_benefit_apply_category;
	@NonNull
	@Column(nullable = false)
	@ManyToOne
	@JoinColumn(name="by_benefit_way_category_code")
	private BenefitWayCategoryVO by_benefit_way_category;
	
	private int by_benefit_limit_amount;
	private String by_benefit_desc;
	private String by_benefit_body;
}
