package com.kh.spring.member.model.service;

import java.security.InvalidAlgorithmParameterException;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.InvalidArgumentsException;
import com.kh.spring.exception.UserIdNotFoundException;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dao.MemberRepository;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceimpl implements MemberService {
	/*
	 * SRP(Single Responsibility Principle)
	 * 단 일 책 임 원 칙 위 반
	 * 하나의 클래스 또는 메소드는 하나의 책임만을 가져야함
	 * 얘가 수정되는 이유는 딱 하나여야 함
	 * 
	 * 해결 방법은 책임 분리 하면 끝난다
	 * 책임 분리 방법 서비스가 BCript에게 의존 하고 있는 상태 때문에  클래스 생성
	 * PasswordEncoder로 클래스 생성 그걸로 다른 클래스로 책임을 분리
	 */
	//@Autowired
	private final SqlSessionTemplate sqlSession;
	//@Autowired
	private final MemberRepository memberRepository;
	//@Autowired
	private final PasswordEncoder passwordEncoder;//문자열을  암호문으로 바꿔주는 메소드
	private final MemberValidator validator; //
	private final MemberMapper mapper;
//	public MemberServiceImpl(
//			SqlSessionTemplate sqlSession,MemberRepository memberRepository,
//			BCryptPasswordEncoder passwordEncoder) {
//		this.sqlSession =sqlSession;
//		this.memberRepository = memberRepository;
//		this.passwordEncoder = passwordEncoder;
//	}
	//그라면 대체 어떻게 어떤 사용자인지 구분을 할까?
	
	@Override
	public  MemberDTO login(MemberDTO member) {
		log.info("나야 로그인~");
	MemberDTO loginMember = mapper.login(member);
	
//	log.info("사용자가 입력한 비밀번호 평문 : {}",member.getUserPwd());
//	log.info("DB에 저장된 암호화된 암호문 : {}" , loginMember.getUserPwd());
	//해싱 특징 같은 평을 가지고 똑같이 해싱을 돌리면 똑같이 나온다
	//아이디만 가지고 조회를 하기 때문에
	//비밀번호 검증 후
	//비밀번호가 유효하다면 ㅇㅋㅇㅋ
	//비밀번호가 유효하지 않다면 이상한데??
	
	return validateLoginMember(loginMember,member.getUserPwd());
	}
	
	private MemberDTO validateLoginMember(MemberDTO loginMember,String userPwd) {
		if(loginMember == null) {
			throw new UserIdNotFoundException("아이디 또는 비밀번호가 틀림");
		}
		if(passwordEncoder.matches(userPwd,loginMember.getUserPwd())){
			return loginMember;
		}
		return null;
		
	}
	
	

	@Override
	public void signUp(MemberDTO member) {
		//사용자가 입력한 값을 가지고 db에 한 행 insert하는 기능을 목적으로 만듬
		//근데 여기서는 만약에
		
		
		//꼼꼼하게 검증 해보자
		//유효값 검증
		//member가 null이냐 아니냐 먼저 체크
//		if(member==null) {
//			throw new NullPointerException("잘못된 접근입니다.");
//			
//		}	
//		if(member.getUserId()==null||member.getUserId().trim().isEmpty()||member.getUserPwd()==null||member.getUserPwd().trim().isEmpty()) {
//			throw new TooLargeValueException("유효하지 않는 값입니다.");
//		}
//		//아이디 값이 20자가 넘으면 안된다고 가정해보자
//		if(member.getUserId().length()>20) {
//			throw new TooLargeValueException("아이디 값이 너무 길어용");
//		}
	//전역에서 일어나는 예외를 처리 하는 컨트롤러를 만듬
		
		//아이디 중복체크 생략
		//회원가입 기능 
		//DAO로가서 INSERT하기
//		log.info("사용자가 입력한 비밀번호 평문: {}",member.getUserPwd());
//		//암호화하기 == 인코더 가지고 .encode()호출
//		log.info("암호화한 후 : {}",passwordEncoder.encode(member.getUserPwd()));
		
		
		validator.validatedValue(member);
		String encPwd = passwordEncoder.encode(member.getUserPwd());
		member.setUserPwd(encPwd);
		//memberRepository.signUp(sqlSession,member);
		//System.out.print(member);
		mapper.signUp(member);
		
	}
	
//정보수정
	@Override
	public void update(MemberDTO member,HttpSession session) {
		
		//본격적인 개발자 영역

		//여기는 멤버 검증 하니까 validate로 가서 검증 함
		//앞단에서 넘어온 ID값과 현재 로그인된 사용자의 ID값이 일치하는가?
		//실제 DB에 ID값이 존재하는 회원인기?
		//USERNAME컬럼에 넣을 값이 USERNAME컬럼 크기보다 크지 않은가?
		//EMAIL컬럼 값이 EMAIL 컬럼 크기보다 크지않은가?
		//(EMAIL컬럼에 넣을 값이 실제 EMAIL형식과 일치하는가?)
		
		
		//까지 성공하면 DB가서 UPDATE
		
		//까지 성공하면 성공한 회원의 정보로 SessionScope에 존재하는 loginMember키값의 Member객체 필드값

		//갱신해주기
		//HttpSession은 사용자의 세션을 저장하고 관리하는객체이다
		
		
		MemberDTO sessionMember =((MemberDTO)session.getAttribute("loginMember"));
		
		validator.validateUpdateMember(member, sessionMember);
		//로그인된 사용자의 정보를 sesisonMember에 담아둠 
		int result = mapper.update(member);
		if(result !=1) {
			throw new AuthenticationException("문제가 발생했습니다. 관리자에게 문의 하세요");
		}
		sessionMember.setUserName(member.getUserName());
		sessionMember.setEmail(member.getEmail());
		
		

	}

	@Override
	public void delete(String userPwd ,HttpSession session) {
		//제1원칙  : 기능이 동작해야 함
		MemberDTO sessionMember = ((MemberDTO)session.getAttribute("loginMember"));
		if(sessionMember == null) {
			throw new  AuthenticationException("로그인부터 하세요");
		}
		if(!passwordEncoder.matches(userPwd, sessionMember.getUserPwd())) {
			throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
		}
		//DELETE FROM MEMBER WHERE USER_ID = 현재 로그인된 사용자의 아이디
		int result = mapper.delete(sessionMember.getUserId());
		if(result != 1) {
			throw new AuthenticationException("관리자에게 문의 하세요");
		}
		//리펙토링
	}

}
