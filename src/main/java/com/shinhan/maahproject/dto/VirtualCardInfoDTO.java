package com.shinhan.maahproject.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class VirtualCardInfoDTO {
	private String tempHiNumber;
	private Timestamp tempHiExpdate;
	private String tempHiCvc;
	private int tempHiStatus;
}
