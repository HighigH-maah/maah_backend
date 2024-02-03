package com.shinhan.maahproject.vo;

import jakarta.annotation.Generated;
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
@ToString(exclude = "member_benefit_member_id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member_benefit")
public class MemberBenefitVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long member_benefit_code;
	
	@ManyToOne
	@JoinColumn(name="member_benefit_member_id")
	private MemberVO member_benefit_member_id;
	
	@ManyToOne
	@JoinColumn(name="member_benefit_by_benefit_code")
	private ByBenefitVO member_benefit_by_benefit_code;
	
	private int member_benefit_used_amount;
	private Boolean member_benefit_is_complete;
	private Integer member_benefit_priority_range;
	
}
