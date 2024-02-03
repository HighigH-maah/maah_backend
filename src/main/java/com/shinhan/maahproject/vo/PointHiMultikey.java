package com.shinhan.maahproject.vo;

import java.io.Serializable;
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
public class PointHiMultikey implements Serializable  {
	@ManyToOne
	@JoinColumn(name = "member_hi_number")
	private MemberCardHiVO member_hi;
	private String point_hi_month;
	
	@Override
	public String toString() {
	    return point_hi_month;
	}
}
