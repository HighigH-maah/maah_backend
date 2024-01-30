package com.shinhan.maahproject.vo;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "member_card_hi")
public class MemberCardHiVO {
	@Id
	private String member_hi_number;
	@NonNull
	@Column(nullable = false)
	private String member_hi_password;
	
	@OneToOne
	@JoinColumn(name = "member_hi_owner")
	private String member_hi_owner;
	
	@EmbeddedId
	@ManyToOne
	@JoinColumn(name = "member_hi_account_id")
	private MemberAccountMultikey member_account_key;
	
	@NonNull
	@Column(nullable = false)
	private int member_hi_status;
	
	@ManyToOne
	@JoinColumn(name="member_hi_image_code")
	private HiCardImageVO hi_image_code;
	private Timestamp member_hi_regdate;
	private Timestamp member_hi_expdate;
	private Timestamp member_hi_paydate;
	@NonNull
	@Column(nullable = false)
	private String member_hi_cvc;
	@NonNull
	@Column(nullable = false)
	@OneToOne
	@JoinColumn(name="member_hi_apply_code")
	private CardApplyVO card_apply;
	private boolean member_hi_is_transport;
	private String member_hi_nickname;
}
