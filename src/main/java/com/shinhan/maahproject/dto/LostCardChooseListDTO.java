package com.shinhan.maahproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LostCardChooseListDTO {
	
	private String memberCardNumber;
	private String memberCardNickname;
	private String cardImageFrontPath;
	private boolean isHiCard;
	
	public LostCardChooseListDTO() {
		// 기본 생성자
	}
	
	public LostCardChooseListDTO(String memberCardNumber, String memberCardNickname, String cardImageFrontPath, boolean isHiCard) {
		this.memberCardNumber = memberCardNumber;
		this.memberCardNickname = memberCardNickname;
		this.cardImageFrontPath = cardImageFrontPath;
		this.isHiCard = isHiCard;
	}

}
