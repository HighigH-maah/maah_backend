package com.shinhan.maahproject.dto;

import com.shinhan.maahproject.vo.MemberVO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class MemberDTO {
	private String memberId;
	private String memberName;
	
	private MemberDTO(String memberId, String memberName) {
		this.memberId = memberId;
		this.memberName = memberName;
	}
	
	public static MemberDTO of(MemberVO member) {
		return new MemberDTO(member.getMemberId(), member.getMemberName());
	}
}
