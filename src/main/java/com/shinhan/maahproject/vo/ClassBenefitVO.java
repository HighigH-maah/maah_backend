package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "class_benefit")
public class ClassBenefitVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long class_benefit_code;
	@NonNull
	@Column(nullable = false)
	private String class_benefit_name;
	private int class_benefit_min_range;
	private Double class_benefit_save_percent;

}
