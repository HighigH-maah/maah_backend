package com.shinhan.maahproject.vo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@ToString(exclude = {"cards", "byRelationBenefitKey"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "by_relation_benefit")
public class ByRelationBenefitVO {
	@EmbeddedId
	ByRelationBenefitMultikey byRelationBenefitKey;
	
	
	
	//repository에서 multikey column 사용하기 위해 선언
	//MapsId 통해서 multikey의 테이블 명과 연결시킴. 
	@ManyToOne
	@JoinColumn(name = "by_relate_benefit_code")
	@MapsId("byRelateBenefitCode")
	private ByBenefitVO benefits;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "by_card_code")
	@MapsId("byCardCode")
	private ByCardVO cards;

}
