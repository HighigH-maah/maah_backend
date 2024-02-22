package com.shinhan.maahproject.dto;

import lombok.Data;

@Data
public class HiInfoChangeDTO {
	private String memberHiNumber;
	private String memberCardHiNickname;
	
	
	//결제일은 나중에 생각
	private int memberHiPaydate;
}
