package com.kh.spring.member.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private Date enrollDate;
//만약에 뭔가를 추가하게 되면 실수할 확률이 있고 유지보수가 힘들기 때문에 lombok사용
	//get,set를 사용하기 위해서 에노테이션 사용 다 에노테이션 사용 
	//lombok을 코드 다이어트 라이브러리라고 말 함
	
	
	

}
