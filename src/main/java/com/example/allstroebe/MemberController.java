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
    private final MemberRepository memberRepository;

    //1. 회원가입 API
    @PostMapping("/join") // 중요한 정보는 POST로 보낸다.
    public String join(@RequestBody MemberDto memberDto) {
        //@RequestBody: 보내온 JSON 데이터를 MemberDto 객체로 넣어 달라는 뜻
        // 중복 체크 (DB에서 조회)

        if(memberRepository.findByemail(memberDto.getEmail()).isPresent()){
            return "이미 존재하는 이메일 입니다.";
        }
        // Entity 객체 생성 후 DB에 저장.
        Member member = new Member();
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());

        memberRepository.save(member); //DB에 저장
        return "회원가입 성공! DB에 저장 되었습니다";
    }

    //2. 로그인 API
    @PostMapping("/login")
    public String login(@RequestBody MemberDto memberDto) {
        // DB에서 아이디로 찾기
      return memberRepository.findByemail(memberDto.getEmail())
              .map(m ->{
                  if (m.getPassword().equals(memberDto.getPassword())){
                      return "로그인 성공! 환영합니다";
                  }
                  return "비밀번호가 틀렸습니다.";
              })
              .orElse("존재하지 않는 사용자입니다.");
    }

}
