package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private int cardApplyCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_apply_member_id")
	private MemberVO member;
	private String cardApplyMemberSocialNumber;
	private Timestamp cardApplyDate;
	private String cardApplyIdIssueDate;
	private Boolean cardApplyIsTermsOfService;
	private Integer cardApplyAnnualIncome;
	private Integer cardApplyPaydate;
	private Integer cardApplyCreditPoint;
	private String cardApplySourceFund;
	private String cardApplyPurpose;
	private Boolean cardApplyIsVerify;
	private String cardApplyEngname;
	private Boolean cardApplyIsInternational;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "card_apply_account_number", referencedColumnName = "member_account_number"),
			@JoinColumn(name = "card_apply_bank_code", referencedColumnName = "member_account_bank_code") })
	private MemberAccountVO memberAccountKey;

	private Boolean cardApplyIsAccountVerify;
	private Integer cardApplyLimitAmount;
	private String cardApplyAddress;
	private String cardApplyPassword;
	private Boolean cardApplyIsTransport;
}
