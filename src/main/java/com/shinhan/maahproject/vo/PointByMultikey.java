package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class PointByMultikey {
	@ManyToOne
	@JoinColumn(name="member_by_number")
	private MemberCardByVO member_card_by;
	private Timestamp point_by_month;
}
