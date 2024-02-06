package com.shinhan.maahproject.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_hi_number")
	private MemberCardHiVO memberHi;
	private String pointHiMonth;
	
	@Override
	public String toString() {
	    return pointHiMonth;
	}
}
