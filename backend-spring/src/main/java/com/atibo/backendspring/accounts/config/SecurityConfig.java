package com.atibo.backendspring.accounts.config;

import com.atibo.backendspring.accounts.jwt.*;
import com.atibo.backendspring.accounts.repository.AccountRepository;
import com.atibo.backendspring.accounts.repository.RefreshRepository;
import com.atibo.backendspring.common.CustomAccessDeniedHandler;
import com.atibo.backendspring.common.CustomAuthenticationEntryPoint;
import com.atibo.backendspring.students.repository.StudentRepository;
import com.atibo.backendspring.students.security.StudentAuthenticationProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    //AuthenticationManager 가 인자로 받을 AuthenticationConfiguration 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final AccountRepository accountRepository;
    private final StudentRepository studentRepository;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final StudentAuthenticationProvider studentAuthenticationProvider;
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, RefreshRepository refreshRepository, AccountRepository accountRepository, StudentRepository studentRepository, CustomAuthenticationProvider customAuthenticationProvider, StudentAuthenticationProvider studentAuthenticationProvider) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.accountRepository = accountRepository;
        this.studentRepository = studentRepository;
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.studentAuthenticationProvider = studentAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    //    계층 권한 설정
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER > ROLE_STUDENT");

        return hierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //      cors 설정
        http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                })));
        //      api 서버의 경우 세션을 jwt 등의 방법으로 관리하므로 disable
        http
                .csrf(AbstractHttpConfigurer::disable);
        //      Form 로그인 방식 disable
        http
                .formLogin(AbstractHttpConfigurer::disable);
        //      http basic 인증 방식 disable
        http
                .httpBasic(AbstractHttpConfigurer::disable);
        //      세션 설정 (JWT 인증/인가 위해서는 STATELESS 상태로 설정하는 것이 중요
        http
                .sessionManagement((auth) -> auth
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // TODO: 경로별 접근 권한 설정 주기
        http
                .authorizeRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/accounts/")
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/gym/*/", "/api/school/")
                        .hasAnyRole("ADMIN")
                        .requestMatchers("/src/**", "/api/school/", "/api/students/*/*/*/check/", "/api/students/attendance/*/*/*/", "/api/school/", "/api/accounts/login/", "/api/accounts/token/refresh/", "/api/accounts/username/check/", "/api/accounts/password/reset/", "/api/students/*/*/*/login/", "/")
                        .permitAll()
                        .requestMatchers("/api/accounts/admin/**", "/api/admin/**", "/api/gym/")
                        .hasAnyRole("ADMIN")
                        .requestMatchers("/api/accounts/**", "/api/students/", "/api/students/inbody/list/", "/api/students/attendance/*/")
                        .hasAnyRole("USER")
                        .requestMatchers("/api/students/*/*/*/password/change/", "/api/students/inbody/*/*/*/", "/api/students/inbody/*/", "/api/students/inbody/*/*/", "/api/gym/*/")
                        .hasAnyRole("STUDENT")
                        .anyRequest().authenticated()
                );

        // LoginFilter 설정
        LoginFilter loginFilter = new LoginFilter(customAuthenticationProvider, jwtUtil, refreshRepository, accountRepository);
        loginFilter.setFilterProcessesUrl("/api/accounts/login/");
        StudentLoginFilter studentLoginFilter = new StudentLoginFilter(studentAuthenticationProvider, jwtUtil, studentRepository);
        studentLoginFilter.setFilterProcessesUrl("/api/students/*/*/*/login/");
        CustomLogoutFilter customLogoutFilter = new CustomLogoutFilter(jwtUtil, refreshRepository, accountRepository);

        http
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler());

        // securityFilter 동작 설정
        http
                .addFilterBefore(new JWTFilter(jwtUtil, studentLoginFilter, loginFilter), LoginFilter.class);
        http
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterAt(studentLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterBefore(customLogoutFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
