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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member_coupon")
public class MemberCouponVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Timestamp member_coupon_code;
	
	@ManyToOne
	@JoinColumn(name="member_id")
	private MemberVO member;
	
	@ManyToOne
	@JoinColumn(name="coupon_store_code")
	private StoreCouponVO coupon_store;
	private int coupon_status;
	
	private Timestamp stamp_one;
	private Timestamp stamp_two;
	private Timestamp stamp_three;
	private Timestamp stamp_four;
	private Timestamp stamp_five;
	private Timestamp stamp_six;
	private Timestamp stamp_seven;
	private Timestamp stamp_eight;
	private Timestamp stamp_nine;
	private Timestamp stamp_ten;
	
}
