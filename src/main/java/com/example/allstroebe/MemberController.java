package com.example.allstroebe;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/members") // 기본 주소는 /api/member 로 시작
@RequiredArgsConstructor // 생성자 주입을 자동으로 해주는 Lombok 어노테이션

public class MemberController {
    //db대신 사용할 임시 저장소 (Map)
    // 이제 Map 대신 Repository 사용.
    // key: 아이디, value: 비밀번호
    private  static  Map<String, String> db = new HashMap<>();

    //1. 회원가입 API
    @PostMapping("/join") // 중요한 정보는 POST로 보낸다.
    public String join(@RequestBody MemberDto memberDto) {
        //@RequestBody: 보내온 JSON 데이터를 MemberDto 객체로 넣어 달라는 뜻
        if(db.containsKey(memberDto.getEmail())){
            return "이미 존재하는 아이디입니다.";
        }
        db.put(memberDto.getEmail(), memberDto.getPassword());
        return "회원가입 성공!";
    }

    //2. 로그인 API
    @PostMapping("/login")
    public String login(@RequestBody MemberDto memberDto) {
        String savedPassword = db.get(memberDto.getEmail());

        if(savedPassword == null){
            return "없는 아이디입니다";
        }
        if(!savedPassword.equals(memberDto.getPassword())){
            return "비밀번호가 틀렸습니다.";
        }
        return "로그인 성공! 환영합니다";
    }

}
