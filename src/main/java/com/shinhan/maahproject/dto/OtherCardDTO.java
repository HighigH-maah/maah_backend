package com.shinhan.maahproject.dto;
import com.shinhan.maahproject.vo.BankVO;

import lombok.Data;

@Data
public class OtherCardDTO {
	private int otherCode;
	private String otherName;
	private Integer otherYearPrice;
	private String otherCategoryList;
	private int otherStatus;
	private String otherImagePath;
	private BankVO otherCompany;
}
