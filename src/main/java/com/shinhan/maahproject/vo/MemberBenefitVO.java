package com.shinhan.maahproject.vo;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@ToString(exclude = "memberBenefitMemberId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member_benefit")
public class MemberBenefitVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberBenefitCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_benefit_member_id")
	private MemberVO memberBenefitMemberId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_benefit_by_benefit_code")
	private ByBenefitVO memberBenefitByBenefitCode;
	
	private int memberBenefitUsedAmount;
	private Boolean memberBenefitIsComplete;
	private Integer memberBenefitPriorityRange;
	
}
