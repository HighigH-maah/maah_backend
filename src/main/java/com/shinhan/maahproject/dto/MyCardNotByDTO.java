package com.shinhan.maahproject.dto;

import java.util.List;

import com.shinhan.maahproject.vo.ByBenefitVO;
import com.shinhan.maahproject.vo.ByCardVO;
import com.shinhan.maahproject.vo.ByRelationBenefitVO;

import lombok.Data;

@Data
public class MyCardNotByDTO{
	
	private String memberId;
	
	private String memberByNumber;
	private String memberCardByNickname;
	
	private String byImagePath;
	//private List<ByBenefitVO> benefits;
	private ByCardDTO byCard;
	
	private String memberHiNumber;
	
	private int byBenefitMinCondition;
	
	private Integer thisMonthSum; //이번달 사용금액

}
