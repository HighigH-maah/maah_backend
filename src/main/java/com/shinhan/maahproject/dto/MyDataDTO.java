package com.shinhan.maahproject.dto;

import java.util.List;

import lombok.Data;

@Data
public class MyDataDTO {
	private MyDataLimitDTO myLimit;
	private List<CategoryBenefitDTO> myCategoryView;
	private MyNextLevelDTO myNextLevel; 

}
