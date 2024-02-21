package com.shinhan.maahproject.dto;

import java.util.List;

import com.shinhan.maahproject.vo.HiCardImageVO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberCardHiShareDTO {
	private String memberHiNickname;
	private HiCardImageVO hiImageCode;
	private Integer memberHiPoint;
	private List<MemberBenefitDTO> memberBenefitList;
}
