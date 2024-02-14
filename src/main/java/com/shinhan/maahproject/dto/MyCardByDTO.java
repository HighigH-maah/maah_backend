package com.shinhan.maahproject.dto;

import java.util.List;

import com.shinhan.maahproject.vo.ByRelationBenefitVO;

import lombok.Data;

@Data
public class MyCardByDTO{
	
	private String memberByNumber;
	private String memberCardByNickname;
	
	private String byImagePath;
	private List<ByRelationBenefitVO> benefits;
	
	private String memberHiNumber;
	
	private int pointByAmount;

}
