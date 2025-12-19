package com.example.allstroebe;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")//프론트엔드 주소
@RestController
@RequestMapping("/api/auth") // 기본 주소는 /api/member 로 시작
@RequiredArgsConstructor // 생성자 주입을 자동으로 해주는 Lombok 어노테이션

public class MemberController {
    //db대신 사용할 임시 저장소 (Map)
    // 이제 Map 대신 Repository 사용.
    // key: 아이디, value: 비밀번호
//    private  static  Map<String, String> db = new HashMap<>();
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    //1. 회원가입 API
    @PostMapping("/signup") // 중요한 정보는 POST로 보낸다.
    public String join(@RequestBody MemberDto memberDto) {
        //@RequestBody: 보내온 JSON 데이터를 MemberDto 객체로 넣어 달라는 뜻
        // 중복 체크 (DB에서 조회)
        if(memberRepository.findByEmail(memberDto.getEmail()).isPresent()){
            return "이미 존재하는 이메일 입니다.";
        }
        // Entity 객체 생성 후 DB에 저장.
        Member member = new Member();
        member.setEmail(memberDto.getEmail());
        // 비밀번호 암호화 후 저장
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        member.setPassword(encodedPassword);

        // 닉네임 저장
        member.setNickname(memberDto.getNickname());

        memberRepository.save(member); //DB에 저장
        return "회원가입 성공! DB에 저장 되었습니다";
    }

    //2. 로그인 API
    @PostMapping("/login")
    public String login(@RequestBody MemberDto memberDto) {
        // DB에서 아이디로 찾기
      return memberRepository.findByEmail(memberDto.getEmail())
              .map(m ->{
                  // 암호화된 비밀번호는 equalse()가 아니라 matches()로 비교해야함.
                  if (passwordEncoder.matches(memberDto.getPassword(), m.getPassword())) {
                      return "로그인 성공! 환영합니다, " + m.getNickname() + "님";
                  }
                  return "비밀번호가 틀렸습니다.";
              })
              .orElse("존재하지 않는 사용자입니다.");
    }

}
