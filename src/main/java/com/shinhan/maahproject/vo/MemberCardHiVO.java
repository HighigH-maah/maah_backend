package com.shinhan.maahproject.vo;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
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

//@EqualsAndHashCode //모든 칼럼을 비교하여 내용 같아야 함
@Getter
@Setter
//@ToString(exclude = {"memberHiOwner", "cardApplyCode"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member_card_hi")
public class MemberCardHiVO {
	
	@Id
	private String memberHiNumber;
	@NonNull
	@Column(nullable = false)
	private String memberHiPassword;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_hi_owner")
	private MemberVO memberHiOwner;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "member_hi_account_number", referencedColumnName = "member_account_number"),
			@JoinColumn(name = "member_hi_account_bank_code", referencedColumnName = "member_account_bank_code") })
	private MemberAccountVO memberAccountKey;
	
	@NonNull
	@Column(nullable = false)
	private int memberHiStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_hi_image_code")
	private HiCardImageVO hiImageCode;
	private Timestamp memberHiRegdate;
	private Timestamp memberHiExpdate;
	private int memberHiPaydate;
	@NonNull
	@Column(nullable = false)
	private String memberHiCvc;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_hi_apply_code")
	private CardApplyVO cardApplyCode;
	private Boolean memberHiIsTransport;
	private String memberHiNickname;
	
	@OneToMany(mappedBy = "memberCardHi", fetch = FetchType.LAZY)
	private List<CardHistoryVO> cardHis;
	
	private int memberHiPoint;
}
