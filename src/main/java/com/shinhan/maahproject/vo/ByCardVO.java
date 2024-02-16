package com.shinhan.maahproject.vo;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "by_card")
public class ByCardVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int byCode;
	@NonNull
	@Column(nullable = false)
	private String byName;
	private Timestamp byRegdate;
	@NonNull
	@Column(nullable = false)
	private Double byOverdueRate;
	private int byYearPrice;
	private int byMinLimit;
	private String byCategoryList;
	@NonNull
	@Column(nullable = false)
	private int byStatus;
	@NonNull
	@Column(nullable = false)
	private String byImagePath;
	private Boolean byIsTransport;
	
	@OneToMany(mappedBy = "byRelationBenefitKey.byCardCode", fetch = FetchType.LAZY)
//	@JsonIgnore
	private List<ByRelationBenefitVO> benefits;


}
