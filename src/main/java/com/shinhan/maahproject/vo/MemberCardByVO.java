package com.shinhan.maahproject.vo;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
//@ToString(exclude = {"connectHiCard", "member", "applyCode"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member_card_by")
public class MemberCardByVO {
	@Id
	private String memberByNumber;
	@NonNull
	@Column(nullable = false)
	private String memberByPassword;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_by_connect_hi")
	@JsonIgnore
	private MemberCardHiVO connectHiCard;
	
	private int memberByLimit;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_by_owner")
	@JsonIgnore
	private MemberVO member;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "member_by_account_number", referencedColumnName = "member_account_number"),
			@JoinColumn(name = "member_by_account_bank_code", referencedColumnName = "member_account_bank_code") })
	@JsonIgnore
	private MemberAccountVO memberAccountKey;
	
	@NonNull
	@Column(nullable = false)
	private int memberByStatus;
	@NonNull
	@ManyToOne
	@JoinColumn(name="member_by_code", nullable = false)
//	@JsonIgnore
	private ByCardVO byCard;
	private Timestamp memberByRegdate;
	private Timestamp memberByExpdate;
	private int memberByPaydate;
	@NonNull
	@Column(nullable = false)
	private String memberByCvc;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_by_apply_code")
	@JsonIgnore
	private CardApplyVO applyCode;
	
	private int memberByRank;
	private int memberByPointGoal;
	private Boolean memberByIsTransport;
	private String memberCardByNickname;
	
	@OneToMany(mappedBy = "memberByNumberMonth.memberByNumber", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<PointByVO> pointBys;
	
	@OneToMany(mappedBy = "memberCardBy", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CardHistoryVO> cardHis;
	
}
