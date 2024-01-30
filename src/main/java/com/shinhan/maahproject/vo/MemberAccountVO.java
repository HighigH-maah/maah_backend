package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode //모든 칼럼을 비교하여 내용 같아야 함
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member_account")
public class MemberAccountVO {
	@EmbeddedId
	MemberAccountMultikey member_account_key;
	
	//member와 연결
	@ManyToOne
	//JoinColumn 이름이 생략되면, '변수 이름_대상 객체의 아이디'로 이름이 생성된다.
	@JoinColumn(name="member_account_member_id")
	private MemberVO member;
	
	private int member_account_balance;
	private Timestamp member_account_regdate;
	private int member_account_status;
	private String member_account_password;

}
