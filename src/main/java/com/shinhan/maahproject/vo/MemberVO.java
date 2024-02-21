package com.shinhan.maahproject.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode // 모든 칼럼을 비교하여 내용 같아야 함
@Getter
@Setter
@ToString(exclude = "classBenefit, memberPassword, memberPasswordSecond")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class MemberVO {
	@Id
	private String memberId;
	@NonNull
	@Column(nullable = false)
	private String memberPassword;
	@NonNull
	@Column(nullable = false)
	private String memberEmail;
	@NonNull
	@Column(nullable = false)
	private Date memberBirthdate;
	@NonNull
	@Column(nullable = false)
	private String memberName;
	@NonNull
	@Column(nullable = false)
	private String memberPhoneNumber;
	@NonNull
	@Column(nullable = false)
	private int memberStatus;

	private String memberPasswordSecond;
	private int memberMileage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_class")
	private ClassBenefitVO classBenefit;

	private String memberAddress;

//	@OneToMany(mappedBy = "memberAccountMemberId", fetch = FetchType.LAZY)
//	private List<MemberAccountVO> memberAccounts;
	
//	@OneToMany(mappedBy = "memberCouponMemberId", fetch = FetchType.LAZY)
//	private List<MemberCouponVO> memberCoupons;
	
	//member_hi_owner
//	@OneToMany(mappedBy = "memberHiOwner", fetch = FetchType.LAZY)
//	private List<MemberCardHiVO> memberHiCard;
	
//	@OneToMany(mappedBy = "memberBenefitMemberId", fetch = FetchType.LAZY)
//	private List<MemberBenefitVO> memberBenefits;

}
