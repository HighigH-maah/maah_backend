package com.shinhan.maahproject.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class CardHistoryDTO {
	public CardHistoryDTO(int byCodeNum, String store, Timestamp date, int amount) {
		this.byCode = byCodeNum;
		this.cardHistoryStore = store;
		this.cardHistoryDate = date;
		this.cardHistoryAmount = amount;
	}
	public CardHistoryDTO(String store, Timestamp date, int amount) {
		this.cardHistoryStore = store;
		this.cardHistoryDate = date;
		this.cardHistoryAmount = amount;
	}
	private int byCode;
	private String cardHistoryStore;
	private Timestamp cardHistoryDate;
	private int cardHistoryAmount;
}
