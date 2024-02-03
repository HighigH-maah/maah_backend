package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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

@EqualsAndHashCode // 모든 칼럼을 비교하여 내용 같아야 함
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "card_apply")
public class CardApplyVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int card_apply_code;

	@ManyToOne
	@JoinColumn(name = "card_apply_member_id")
	private MemberVO member;
	// 암호화 필요
	private String card_apply_member_social_number;
	private Timestamp card_apply_date;
	private String card_apply_id_image_path;
	private Boolean card_apply_is_terms_of_service;
	private Integer card_apply_annual_income;
	private Integer card_apply_paydate;
	private Integer card_apply_credit_point;
	private String card_apply_source_fund;
	private String card_apply_purpose;
	private Boolean card_apply_is_verify;
	private String card_apply_engname;
	private Boolean card_apply_is_international;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "card_apply_account_number", referencedColumnName = "member_account_number"),
			@JoinColumn(name = "card_apply_bank_code", referencedColumnName = "member_account_bank_code") })
	private MemberAccountVO member_account_key;

	private Boolean card_apply_is_account_verify;
	private Integer card_apply_limit_amount;
	private String card_apply_address;
	private String card_apply_password;
	private Boolean card_apply_is_transport;
}
