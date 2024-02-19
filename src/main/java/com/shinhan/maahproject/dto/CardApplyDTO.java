package com.shinhan.maahproject.dto;

import lombok.Data;

@Data
public class CardApplyDTO {
	private String memberId;
	private String card;
	private String type;
	private String cardApplyMemberSocialNumber;
	private String cardApplyDate;
	private String cardApplyIdIssueDate;
	private Boolean cardApplyIsTermsOfService;
	private String cardApplyAnnualIncome;
	private String cardApplyPaydate;
	private String cardApplyCreditPoint;
	private String cardApplySourceFund;
	private String cardApplyPurpose;
	private Boolean cardApplyIsVerify;
	private String cardApplyEngname;
	private Boolean cardApplyIsInternational;
	private Boolean cardApplyIsAccountVerify;
	private String cardApplyLimitAmount;
	private String cardApplyAddress;
	private String cardApplyPassword;
	private String cardApplyIsTransport;
}
