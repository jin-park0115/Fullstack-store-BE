package com.example.allstroebe.util;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 헤더에서 토큰 꺼내기
        String authorizationHeader = request.getHeader("Authorization");

        // 2. 토큰이 있고, "Bearer "로 시작하는지 확인
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // "Bearer " 이후의 문자열만 추출

            try {
                // 3. 토큰 해석 (위조되었거나 만료되면 여기서 에러 남)
                Claims claims = jwtUtil.extractClaims(token);
                String email = claims.getSubject(); // 이메일 꺼내기
                System.out.println("추출된 이메일: " + email);
                // 4. 인증 정보가 없으면 Context에 등록 (이게 핵심!)
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // "이 사용자는 인증되었습니다"라고 도장 찍는 과정
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null,
                                    List.of(new SimpleGrantedAuthority("ROLE_USER")));
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("인증 객체 등록 완료: " + SecurityContextHolder.getContext().getAuthentication().getName());
                }
            } catch (Exception e) {
                // 토큰이 유효하지 않으면 그냥 통과시킴 (Controller에서 거름)
                System.out.println("토큰 검증 실패: " + e.getMessage());
            }
        }

        // 다음 필터로 넘김
        filterChain.doFilter(request, response);
    }
}