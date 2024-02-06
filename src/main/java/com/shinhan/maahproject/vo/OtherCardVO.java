package com.shinhan.maahproject.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Table(name = "other_card")
public class OtherCardVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int otherCode;
	
	private String otherName;
	private Integer otherYearPrice;
	private String otherCategoryList;
	private int otherStatus;
	private String otherImagePath;
	@ManyToOne
	@JoinColumn(name="other_company")
	private BankVO otherCompany;
	
	
}
