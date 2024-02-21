package com.shinhan.maahproject.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CardApplyDTO {
	private String memberId;
	private String card;
	private String type;
	private String cardApplyMemberSocialNumber;
	private Timestamp cardApplyDate;
	private String cardApplyIdIssueDate;
	private Boolean cardApplyIsTermsOfService;
	private Integer cardApplyAnnualIncome;
	private Integer cardApplyPaydate;
	private Integer cardApplyCreditPoint;
	private String cardApplySourceFund;
	private String cardApplyPurpose;
	private String bankCode;
	private String accountNumber;
	private Boolean cardApplyIsVerify;
	private String cardApplyEngname;
	private Boolean cardApplyIsInternational;
	private Boolean cardApplyIsAccountVerify;
	private Integer cardApplyLimitAmount;
	private String cardApplyAddress;
	private String cardApplyPassword;
	private Boolean cardApplyIsTransport;
}
