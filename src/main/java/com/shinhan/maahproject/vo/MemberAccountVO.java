package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class MemberAccountVO {
	@EmbeddedId
	MemberAccountMultikey memberAccountKey;
	
	//member와 연결
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_account_member_id")
	private MemberVO memberAccountMemberId;
	
	private int memberAccountBalance;
	private Timestamp memberAccountRegdate;
	private int memberAccountStatus;
	private String memberAccountPassword;

}
