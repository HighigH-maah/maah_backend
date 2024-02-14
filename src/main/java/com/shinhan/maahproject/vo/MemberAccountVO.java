package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode //모든 칼럼을 비교하여 내용 같아야 함
@Getter
@Setter
@ToString(exclude = "memberAccountMemberId")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member_account")
@IdClass(MemberAccountMultikey.class)
public class MemberAccountVO {
	
	@Id
	@Column(name = "member_account_number")
	private String memberAccountNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_account_bank_code")
	@Id
	private BankVO bank;
	
	
	
	
	
	//member와 연결
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_account_member_id")
	@JsonIgnore
	private MemberVO memberAccountMemberId;
	
	private int memberAccountBalance;
	private Timestamp memberAccountRegdate;
	private int memberAccountStatus;
	private String memberAccountPassword;
	
	
	

}
