package com.example.allstroebe;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 사용자 이름으로 회원을 찾는 기능 추가
    Optional<Member> findByEmail(String email);

}
