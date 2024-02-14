package com.shinhan.maahproject.dto;


import java.sql.Timestamp;

import com.shinhan.maahproject.vo.MemberVO;

import lombok.Data;

@Data
public class MemberAccountDTO {
	private MemberVO memberAccountMemberId;
	private int memberAccountBalance;
	private Timestamp memberAccountRegdate;
	private int memberAccountStatus;
	private String memberAccountPassword;
}
