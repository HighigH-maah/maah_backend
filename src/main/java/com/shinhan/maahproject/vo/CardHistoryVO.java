package com.shinhan.maahproject.vo;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode //모든 칼럼을 비교하여 내용 같아야 함
@Getter
@Setter
@ToString(exclude = {"member_card_hi", "member_card_by"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "card_history")
public class CardHistoryVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long card_history_id;
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
