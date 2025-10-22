package com.kh.spring.member.model.service;

import org.springframework.stereotype.Component;

import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {
	private final MemberMapper memberMapper;
//멤버 검증하는 서비스
	private void checkNull(MemberDTO member) {
		if(member==null) {
			throw new NullPointerException("잘못된 접근입니다.");
			
		}	
	}
	private void checkLength(MemberDTO member) {
		if(member.getUserId().length()>20) {
			throw new TooLargeValueException("아이디 값이 너무 길어용");
		}
	}
	private void checkBlank(MemberDTO member) {
		if(member.getUserId()==null||member.getUserId().trim().isEmpty()||member.getUserPwd()==null||member.getUserPwd().trim().isEmpty()) {
			throw new TooLargeValueException("유효하지 않는 값입니다.");
		}
	}
		public void validatedValue(MemberDTO member) { //검증하는 메소드
	
		
		checkNull(member);
		checkLength(member);
		checkBlank(member);
		}
		
		public void validateUpdateMember(MemberDTO member, MemberDTO sessionMember) {
			checkNull(member);
			checkNull(sessionMember);
		if(!member.getUserId().equals(sessionMember.getUserId())) {
			//db에 있는 멤버에 있는 정보와 사용자가 입력한 정보가 일치 하지 않는 다면
			
			throw new AuthenticationException("권한없는 접근입니다.");
		}
		checkNull(memberMapper.login(member));
		
		}
		
		
		
		
		
		
		
}
//		if(member==null) {
//			throw new NullPointerException("잘못된 접근입니다.");
//			
//		}	
//		if(member.getUserId()==null||member.getUserId().trim().isEmpty()||member.getUserPwd()==null||member.getUserPwd().trim().isEmpty()) {
//			throw new TooLargeValueException("유효하지 않는 값입니다.");
//		}
		//아이디 값이 20자가 넘으면 안된다고 가정해보자
//		if(member.getUserId().length()>20) {
//			throw new TooLargeValueException("아이디 값이 너무 길어용");
//		}
		
	
	
	
	
	

