package com.atibo.backendspring.accounts.config;

import com.atibo.backendspring.accounts.domain.AccountRole;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
    @Bean
    //    계층 권한 설정
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        return hierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // TODO: 경로별 접근 권한 설정 주기
        http
                .authorizeRequests(auth -> auth
                        .requestMatchers("/", "/login", "/api/accounts", "/error").permitAll()
                        .requestMatchers("/user").hasAnyRole("USER")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                );

        http
                .formLogin(auth -> auth.loginProcessingUrl("/api/accounts/login/")
                                       .permitAll()
                        .defaultSuccessUrl("/")
                );

        //        api 서버의 경우 세션을 jwt 와 비슷하게 관리하므로 disable 해도 ㄱㅊ음
        http
                .csrf(auth -> auth.disable());

        http
                .sessionManagement((auth) -> auth
                        //                        하나의 아이디에 대한 다중 로그인 허용 개수
                        .maximumSessions(1)
                        //                        다중 로그인 개수를 초과하였으 경우 처리 방법 true: 새로운 로그인 차단, false: 기존 세션 하나 삭제
                        .maxSessionsPreventsLogin(true));

        http
                .sessionManagement((auth) -> auth
                        //                        세션 고정 보호
                        .sessionFixation().changeSessionId());

        return http.build();
    }
}
