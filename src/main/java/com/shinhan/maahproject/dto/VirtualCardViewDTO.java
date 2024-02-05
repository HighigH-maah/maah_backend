package com.shinhan.maahproject.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class VirtualCardViewDTO {
	private String temp_hi_number;
	private Timestamp temp_hi_expdate;
	private String temp_hi_cvc;
}
