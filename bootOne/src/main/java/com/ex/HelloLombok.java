package com.ex;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor	// 모든 매개변수를 필요로 하는 생성사 자동 생성
@Getter	// get() 자동생성
//@Setter // set() 자동생성
public class HelloLombok {
	
	private final String hello; // get() 자동생성
	private final int lombok;   // set() 자동생성
	
	public static void main(String[] args) {
		HelloLombok h1 = new HelloLombok("하이", 200);
//		h1.setHello("헬로");
//		h1.setLombok(100);
		
		System.out.println(h1.getHello());
		System.out.println(h1.getLombok());
	}
}
