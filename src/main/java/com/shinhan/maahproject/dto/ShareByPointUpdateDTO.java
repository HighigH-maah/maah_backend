package com.shinhan.maahproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ShareByPointUpdateDTO {
	private String memberId;
	private String byCardNumber;
	private int amount;
	private int hiAmount;
	private int byAmount;
}
