package com.example.allstroebe;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    //서버만 알고 있어야 하는 비밀키 (최소 32자 이상의 랜덤 문자열하는게 좋음 추천!)
    private final String SECRET_KEY = "your-very-secret-key-should-be-very-long-and-secure";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    //토큰 유효 시간 (1시간)
    private final long EXPIRATION_TIME = 1000 * 60 * 60L;

    // 1.토큰 생성하기
    public String createToken(String email, String nickname) {
        return Jwts.builder()
                .setSubject(email) //토큰 주인
                .claim("nickname", nickname) //내부에 저장할 데이터(닉네임)
                .setIssuedAt(new Date()) //발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //만료 시간
                .signWith(key, SignatureAlgorithm.HS256) //암호화 알고리즘 해시알고리즘
                .compact();
    }

    // 2. 토큰에서 정보 꺼내기 (나중에 사용할 거)
    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
