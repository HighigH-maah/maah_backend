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
@ToString(exclude = "class_benefit, member_password, member_password_second")
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
	private Date member_birthdate;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_class")
	private ClassBenefitVO class_benefit;

	private String member_address;

	@JsonManagedReference
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<MemberAccountVO> memberAccounts;

}
