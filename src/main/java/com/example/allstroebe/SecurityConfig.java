package com.example.allstroebe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF 보안 비활성화 (Postman/프론트엔드 테스트를 위해 필수)
                .csrf(csrf -> csrf.disable())

                // 2. HTTP 기본 인증 및 폼 로그인 비활성화
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())

                // 3. 모든 경로에 대해 권한 검사 없이 허용
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}