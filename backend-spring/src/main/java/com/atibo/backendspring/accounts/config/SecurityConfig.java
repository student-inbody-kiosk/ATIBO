package com.atibo.backendspring.accounts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/accounts").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                    //  .anyRequest().authenticated()
                );  // TODO: 경로별 접근 권한 설정 주기

        http
                .formLogin(auth -> auth.loginProcessingUrl("/login")
                                       .permitAll()
                );

        http
                .csrf(auth -> auth.disable());
        return http.build();
    }
}
