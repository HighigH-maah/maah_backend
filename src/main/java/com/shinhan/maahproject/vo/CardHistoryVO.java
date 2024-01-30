package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NonNull;

public class CardHistoryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String card_history_id;
	@NonNull
	@Column(nullable = false)
	private Timestamp card_history_date;
	@NonNull
	@Column(nullable = false)
	private int card_history_amount;
	@NonNull
	@Column(nullable = false)
	private String card_history_store;
	@ManyToOne
	@JoinColumn(name="card_history_hi")
	//카드는 가져오는데 카드에서 결제 리스트는 못 가져옴
	private MemberCardHiVO member_card_hi;
	@ManyToOne
	@JoinColumn(name="card_history_by")
	private MemberCardByVO member_card_by;
	private String card_history_checknum;
	@NonNull
	@Column(nullable = false)
	private int card_history_status;
}
