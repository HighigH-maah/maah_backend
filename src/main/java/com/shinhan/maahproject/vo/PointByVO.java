package com.shinhan.maahproject.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@ToString(exclude = {"memberByNumber"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "point_by")
@IdClass(PointByMultikey.class)
public class PointByVO {

	@Id
	@ManyToOne
	@JoinColumn(name = "member_by_number")
	private MemberCardByVO memberByNumber;
	@Id
	@Column(name = "point_by_month")
	private String pointByMonth;
	
	
	@NonNull
	@Column(nullable = false)
	private int pointByAmount;
	
}