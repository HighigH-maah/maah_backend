package com.shinhan.maahproject.dto;

import java.util.List;

import com.shinhan.maahproject.vo.MemberCardHiVO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ByCardDetailDTO {

public ByCardDetailDTO(String memberByNumber, int byCode, String byName, String memberCardByNickname, String byImagePath,
int pointByAmount, String pointByMonth, int byBenefitMinCondition, String connectHiCard) {

this.memberByNumber = memberByNumber;
this.byCode = byCode;
this.byName = byName;
this.memberCardByNickname = memberCardByNickname;
this.byImagePath = byImagePath;
this.pointByMonth = pointByMonth;
this.pointByAmount = pointByAmount;
this.byBenefitMinCondition = byBenefitMinCondition;
this.connectHiCard = connectHiCard;
}



private List<String> byCategoryList;
private List<String> benefitList;
private String memberByNumber;
private int byCode;
private String byName;
private String memberCardByNickname;
private String byImagePath;
private String pointByMonth; //byCard pointmonth
private int pointByAmount; //byCard 포인트
private int byBenefitMinCondition; //byCard 전월실적
private String connectHiCard;

}