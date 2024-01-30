package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class PointHiMultikey {
	@ManyToOne
	@JoinColumn(name="member_hi_number")
	private MemberCardHiVO member_card_hi;
	private Timestamp point_by_month;
}
