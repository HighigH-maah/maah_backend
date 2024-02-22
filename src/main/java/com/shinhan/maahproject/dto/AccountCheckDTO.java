package com.shinhan.maahproject.dto;

import lombok.Data;

@Data
public class AccountCheckDTO {

	private String memberId;
	private String memberName;
	private String bankCode;
	private String bankName; //계좌번호
	private String accountChkYn;
	private String cardNumber;
	private boolean isHi;

}
