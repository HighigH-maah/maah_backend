package com.shinhan.maahproject.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.maahproject.repository.MemberCardByRepository;
import com.shinhan.maahproject.repository.MemberCardHiRepository;
import com.shinhan.maahproject.repository.MemberRepository;
import com.shinhan.maahproject.repository.PointByRepository;
import com.shinhan.maahproject.vo.MemberCardByVO;
import com.shinhan.maahproject.vo.MemberCardHiVO;
import com.shinhan.maahproject.vo.MemberVO;
import com.shinhan.maahproject.vo.PointByVO;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MonthlyService {
	
	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	MemberCardByRepository mbRepo;
	
	@Autowired
	MemberCardHiRepository mhRepo;
	
	@Autowired
	PointByRepository pbRepo;
	
	
	
	
	
	
	//멤버 별 월말 잔여 포인트 처리(바이 순위별)
	//@Transactional
	public void remainPointShare(String memberId) {
		MemberVO member = mRepo.findById(memberId).orElse(null);
		
		List<MemberCardHiVO> mhicards = mhRepo.findByMemberHiOwnerAndMemberHiStatus(member, 0);
		MemberCardHiVO mhicard = null;
		//hicard 못찾음
		if(mhicards.size()==0) {
			return;
		}
		mhicard = mhicards.get(0);
		//hipoint 없음
		if(mhicard.getMemberHiPoint() == 0) {
			return;
		}
		int mhipoint = 0; 
		
		List<MemberCardByVO> mbycards = mbRepo.findByMemberAndMemberByStatus(member, 0);
		
		
		String currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
		List<PointByVO> pbList = pbRepo.findByMemberByNumberListAndPointByMonth(mbycards, currentYearMonth);
		log.info(mbycards.toString());
		MemberCardByVO bycard = null;
		
		//for문 돌면서 목표치까지 채움
		for(PointByVO pointby : pbList) {
			mhipoint = mhicard.getMemberHiPoint();
			bycard = pointby.getMemberByNumber();
			if(mhipoint <= 0) {
				break;
			}
			//자신의 차례에서 목표 포인트보다 현재 포인트가 적을 경우
			if(bycard.getMemberByPointGoal() < pointby.getPointByAmount()) {
				
			}
			
			
		}
		mhipoint = mhicard.getMemberHiPoint();
		//한바퀴 돌아서 모든 바이카드의 목표를 채웠음에도 포인트가 남았다면, 나머지 전부 1번에 채운다.
		if(mhipoint > 0) {
			pbList.get(0).setPointByAmount(pbList.get(0).getPointByAmount()+mhipoint);
			mhicard.setMemberHiPoint(0);
		}
		
	}
	
	@Transactional
	public boolean updatePointProcess(MemberCardHiVO mhicard, MemberCardByVO bycard, PointByVO pointby) {
		int mhipoint = mhicard.getMemberHiPoint();
		//hipoint가 0이하일 시 종료가능 false
		if(mhipoint <= 0) {
			return false;
		}
		//목표를 이미 채웠을 경우 다음 카드 확인 필요 true
		if(bycard.getMemberByPointGoal() >= pointby.getPointByAmount()) {
			return true;
		}
		//목표를 아직 못채움
		else {
			//a
			
		}
		
		
		
		
		return false;
	}
	
	
	
	
	//멤버 별 월초 포인트 기준 member-benefit 재설정
	
	
}
