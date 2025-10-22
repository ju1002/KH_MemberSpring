package com.kh.spring.exception;

public class TooLargeValueException extends RuntimeException {
	public TooLargeValueException(String message) {
	super(message);
	//실행 중에 일어나는 예외는 모두 RuntimeException이다.
	//사용자가 입력한 아이디가 20자가 넘었을 때 예외를 발생 시키겠따
	}

}
