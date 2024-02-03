package com.shinhan.maahproject.vo;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = {"connect_hi_card", "member", "apply_code"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member_card_by")
public class MemberCardByVO {
	@Id
	private String member_by_number;
	@NonNull
	@Column(nullable = false)
	private String member_by_password;
	
	@ManyToOne
	@JoinColumn(name="member_by_connect_hi")
	private MemberCardHiVO connect_hi_card;
	
	private int member_by_limit;
	
	@ManyToOne
	@JoinColumn(name = "member_by_owner")
	private MemberVO member;
	
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "member_by_account_number", referencedColumnName = "member_account_number"),
			@JoinColumn(name = "member_by_account_bank_code", referencedColumnName = "member_account_bank_code") })
	private MemberAccountVO member_account_key;
	
	@NonNull
	@Column(nullable = false)
	private int member_by_status;
	@NonNull
	@ManyToOne
	@JoinColumn(name="member_by_code", nullable = false)
	private ByCardVO by_card;
	private Timestamp member_by_regdate;
	private Timestamp member_by_expdate;
	private int member_by_paydate;
	@NonNull
	@Column(nullable = false)
	private String member_by_cvc;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_by_apply_code")
	private CardApplyVO apply_code;
	
	private int member_by_rank;
	private int member_by_point_goal;
	private Boolean member_by_is_transport;
	private String member_card_by_nickname;
	
	@OneToMany(mappedBy = "member_by_number_month.member_by_number", fetch = FetchType.LAZY)
	private List<PointByVO> point_bys;
	
	@OneToMany(mappedBy = "member_card_by", fetch = FetchType.LAZY)
	private List<CardHistoryVO> card_his;
	
}
