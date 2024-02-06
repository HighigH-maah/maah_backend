package com.shinhan.maahproject.vo;

import java.io.Serializable;
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
public class PointByMultikey implements Serializable {
	private String memberByNumber;
	private String pointByMonth;
	
	@Override
	public String toString() {
	    return pointByMonth;
	}
}
