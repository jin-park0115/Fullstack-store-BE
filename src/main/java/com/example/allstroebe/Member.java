package com.example.allstroebe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {
    @Id //기본키(Primary key)설정
    @GeneratedValue(strategy =  GenerationType.IDENTITY) //번호 자동 증가
    private Long id;

    @Column(unique = true) // 아이디 중복 방지
    private String email;
    private String password;
}
