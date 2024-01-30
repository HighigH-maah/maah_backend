package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
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
	private String temp_hi_number;
	@ManyToOne
	@JoinColumn(name ="member_hi_number")
	private MemberCardHiVO member_card_hi;
	private int temp_hi_status;
	private Timestamp temp_hi_expdate;
	private String temp_hi_cvc;
}
