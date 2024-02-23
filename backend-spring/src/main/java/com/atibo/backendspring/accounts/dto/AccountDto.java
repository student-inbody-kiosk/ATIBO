package com.atibo.backendspring.accounts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
public class AccountDto {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
//    @Pattern(regexp = "^(?=.*[A-Za-z])([A-Za-z/d]{5,20})$")
    @Size(min = 5, max = 20)
    private String username;

    private String name;

    private String password;

    private String email;

    private String comment;

    @Builder
    public AccountDto(String username, String name, String email, String comment, String password) {

        this.username = username;
        this.name = name;
        this.email = email;
        this.comment = comment;
        this.password = password;
    }
}
