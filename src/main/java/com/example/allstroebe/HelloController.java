package com.example.allstroebe;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //1. 이 클래스가 웹 요청을 처리한다고 알려준다.
public class HelloController {
    @GetMapping("/hello") //2. "http://localhost:8080/hello" 주소로 오면 이 함수 실행
    public String sayHello() {
        return "안녕하세요 Spring boot 시작을 축하합니다."; //3. 화면에 띄울 문자열
    }
}
