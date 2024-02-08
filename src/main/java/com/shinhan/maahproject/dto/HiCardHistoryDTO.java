package com.shinhan.maahproject.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class HiCardHistoryDTO {
	private String cardHistoryStore;
	private Timestamp cardHistoryDate;
	private int cardHistoryAmount;
}
