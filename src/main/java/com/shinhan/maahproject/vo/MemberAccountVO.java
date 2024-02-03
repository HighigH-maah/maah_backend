package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@ToString(exclude = "member_account_member_id")
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
	@JoinColumn(name="member_account_member_id")
	private MemberVO member_account_member_id;
	
	private int member_account_balance;
	private Timestamp member_account_regdate;
	private int member_account_status;
	private String member_account_password;

}
