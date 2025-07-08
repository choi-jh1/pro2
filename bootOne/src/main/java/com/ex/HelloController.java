package com.ex;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller  // 이 클래스가 컨트롤러의 기능을 수행한다는 것을 부트에게 알려줌 - web.xml 에서 했던 서블릿 설정 
public class HelloController {
	// http 프로토콜에서 데이터를 서버로 전송하는 방식 Get/Post 
	// properties에서 uri = Action
	@GetMapping("/hello") // http://localhost:8080/hello <- uri 가 get 방식으로 hello 요청하면 아래 메서드 실행
	@ResponseBody // 브라우저에서 리턴돼서 돌아오는 값을 보여줌 ("Hello Springboot!!")
	public String hello() {
		return "Hello Springboot!!";
	}
}
