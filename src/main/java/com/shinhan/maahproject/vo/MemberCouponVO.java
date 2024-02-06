package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

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
@ToString(exclude = "memberCouponMemberId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member_coupon")
public class MemberCouponVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int memberCouponCode;
	
	@ManyToOne
	@JoinColumn(name="member_coupon_member_id")
	private MemberVO memberCouponMemberId;
	
	@ManyToOne
	@JoinColumn(name="coupon_store_code")
	private StoreCouponVO couponStore;
	private Integer couponStatus;
	
	private Timestamp stampOne;
	private Timestamp stampTwo;
	private Timestamp stampThree;
	private Timestamp stampFour;
	private Timestamp stampFive;
	private Timestamp stampSix;
	private Timestamp stampSeven;
	private Timestamp stampEight;
	private Timestamp stampNine;
	private Timestamp stampTen;
	
}
