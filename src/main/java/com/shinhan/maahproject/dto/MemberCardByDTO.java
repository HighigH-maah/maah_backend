package com.shinhan.maahproject.dto;

import com.shinhan.maahproject.vo.ByCardVO;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberCardByDTO {
	private String memberByNumber;
	private ByCardDTO byCard;
	private int memberByRank;
	private int memberByPointGoal;
	private Boolean memberByIsTransport;
	private String memberCardByNickname;
	private int pointByAmount;
}
