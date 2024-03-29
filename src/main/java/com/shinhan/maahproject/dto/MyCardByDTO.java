package com.shinhan.maahproject.dto;

import java.util.List;

import com.shinhan.maahproject.vo.ByBenefitVO;
import com.shinhan.maahproject.vo.ByCardVO;
import com.shinhan.maahproject.vo.ByRelationBenefitVO;

import lombok.Data;

@Data
public class MyCardByDTO{
	
	private String memberId;
	
	private String memberByNumber;
	private String memberCardByNickname;
	
	private String byImagePath;
	//private List<ByBenefitVO> benefits;
	//private ByCardVO byCard;
	private ByCardDTO byCard;
	
	private String memberHiNumber;
	
	private int pointByAmount;
	private int byBenefitMinCondition;
	
	//private int byMinLimit;

}
