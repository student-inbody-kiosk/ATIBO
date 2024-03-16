package com.atibo.backendspring.accounts.dto;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.domain.AccountRole;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


public class AccountDto {

    @AllArgsConstructor
    @Builder
    @Getter
    public static class RequestDto {
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])([A-Za-z/d]{5,20})$")
        @Size(min = 5, max = 20)
        private String username;
        private String name;
        private String password;
        private String email;
        private String comment;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class ResponseDto {
        private Long id;
        private String username;
        private String name;
        private String email;
        private AccountRole role;
        private String comment;

        public ResponseDto toResponseDto(Account account) {
            return ResponseDto.builder()
                      .id(account.getId())
                      .username(account.getUsername())
                      .name(account.getName())
                      .email(account.getEmail())
                      .role(account.getRole())
                      .comment(account.getComment()).
                      build();
        }
    }

    @Getter
    @Setter
    private static class JoinFailDto {
        //TODO 정규식 처리
        private String username = "user with this username already exists.";
        private String name = "Ensure this field has no more than 5 characters.";
        private String email = "user with this email already exists.";
    }

    public static class LoginRequestDto {

        public String username;
        public String password;
    }

    @Builder
    public static class LoginResponseDto {

        @JsonProperty("accessToken")
        private String accessToken;
        @JsonProperty("refreshToken")
        private String refreshToken;
    }

    @Getter
    @Setter
    public static class tokenDto {

        private String username;
        private String refreshToken;
    }
}
