package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "temp_hi")
public class TempHiVO {
	@Id
	private String tempHiNumber;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="member_hi_number")
	@JsonIgnore
	private MemberCardHiVO memberCardHi;
	private int tempHiStatus;
	private Timestamp tempHiExpdate;
	private String tempHiCvc;
}
