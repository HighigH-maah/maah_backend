package com.shinhan.maahproject.dto;

import lombok.Data;

@Data
public class ByCardInfoChangeDTO {
	private String memberByNumber;
	private int memberByRank;
	private int memberByPointGoal;
	private String memberCardByNickname;
	
	
	//결제일은 나중에 생각
	private int memberByPaydate;
}
