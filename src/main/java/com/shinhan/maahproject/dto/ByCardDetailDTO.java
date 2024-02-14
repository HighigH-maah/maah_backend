package com.shinhan.maahproject.dto;
import java.sql.Timestamp;
import java.util.List;

import com.shinhan.maahproject.vo.ByRelationBenefitVO;

import lombok.Data;

@Data
public class ByCardDetailDTO {
	private int byCode;
	private String byName;
	private Timestamp byRegdate;
	private Double byOverdueRate;
	private int byYearPrice;
	private int byMinLimit;
	private List<String> byCategoryList;
	private int byStatus;
	private String byImagePath;
	private Boolean byIsTransport;
	private List<String> benefitList;
}
