package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "member")
public class MemberVO {
	@Id
	private String member_id;
	@NonNull
	@Column(nullable = false)
	private String member_password;
	@NonNull
	@Column(nullable = false)
	private String member_email;
	@NonNull
	@Column(nullable = false)
	private Timestamp member_birthdate;
	@NonNull
	@Column(nullable = false)
	private String member_name;
	@NonNull
	@Column(nullable = false)
	private String member_phone_number;
	@NonNull
	@Column(nullable = false)
	private int member_status;
	
	private String member_password_second;
	private int member_mileage;
	
	@ManyToOne
	@JoinColumn(name="member_class")
	private ClassBenefitVO class_benefit;
	private String member_address;
}
