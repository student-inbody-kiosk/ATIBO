package com.atibo.backendspring.accounts.dto;

import java.util.UUID;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.domain.AccountRole;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;

import jakarta.persistence.GeneratedValue;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


public class AccountDto {

    @AllArgsConstructor
    @Builder
    @Getter
    public static class RequestDto {

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
        private UUID id;
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
                              .comment(account.getComment())
                              .build();
        }
    }

    @Getter
    public static class JoinFailDto {
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

        @JsonProperty("user")
        private String username;
        private String refreshToken;
    }

    public static class reissueDto {
        @JsonProperty("accessToken")
        private String accessToken;

        public reissueDto(String token) {

            this.accessToken = token;
        }
    }

    @Getter
    public static class checkUserNameDto {

        private String username;

        public checkUserNameDto(String username) {

            this.username = username;
        }
    }

    public static final class checkUserNameResponse {

        @JsonProperty("duplicate")
        private boolean duplicate = false;
    }


}
