package com.kh.spring.member.model.service;

import javax.servlet.http.HttpSession;

import com.kh.spring.member.model.dto.MemberDTO;


public interface MemberService {
	
	//interface가 가질 수 있는 메소드 
	//추상 메소드 :중괄호 없음 , 세미콜론으로 끝남 , 선언만 있음(메소드 이름만 있고 안에 내용이 앖음)
	//default 메소드 
	//static 메소드
//인터페이스에서 로그인 기능의 반환타입과 + 매개변수는 생각해 둘 수 있음
	MemberDTO login(MemberDTO member);
	//회원가입
	//올 수 있는 반환 타입
	//Mybatis: 정수값을 반환한다., 아무것도 반환하지 않음(void) ==> 에외 처리를 빡빡하게 하겠음
	//Hibernate: 가입된 회원의 정보를 반환해줌 / 실패시 null
	//SqlSession session = Template.getSqlSession();
	//Bean에 등록함
	
	void signUp(MemberDTO member);
	
	//정보수정
	void update(MemberDTO member,HttpSession session);
	
	
	//탈퇴
	void delete(String userPwd,HttpSession session);
	
	
}
