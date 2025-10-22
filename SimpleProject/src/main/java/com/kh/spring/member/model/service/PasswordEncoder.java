package com.kh.spring.member.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component 
//bean에 등록하는 에노테이션
@RequiredArgsConstructor
public class PasswordEncoder {
	//책임 분리하기 위한 클래스
	private final BCryptPasswordEncoder passwordEncoder;
	
	public String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
		
	}
	public boolean matches(String rawPassword , String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

}
