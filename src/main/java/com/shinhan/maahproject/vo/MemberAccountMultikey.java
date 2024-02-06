package com.shinhan.maahproject.vo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class MemberAccountMultikey implements Serializable {
	@Column(name = "member_account_number")
	private String memberAccountNumber;
	@ManyToOne
	@JoinColumn(name="member_account_bank_code")
	private BankVO bank;
}
