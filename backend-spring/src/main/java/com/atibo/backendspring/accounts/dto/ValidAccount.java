package com.atibo.backendspring.accounts.dto;

import java.util.regex.Pattern;
import com.atibo.backendspring.accounts.repository.AccountRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidAccount {

    private final AccountRepository accountRepository;

    private static final String USER_NAME_PATTERN = "^(?=.*[A-Za-z])([A-Za-z\\d]{5,20}$)";      //한글과 영어 조합 5~20자. 최소 1개의 문자 포함
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";    // email 형식
    //문자, 숫자, 특수문자 조합 8~24자. 각각 1개의 문자 포함
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\\(\\)\\[\\]\\{\\}\\|\\\\`~!@#\\$%\\^&\\*\\-+=;:,<>\\./\\?])[A-Za-z\\d\\(\\)\\[\\]\\{\\}\\|\\\\`~!@#\\$%\\^&\\*\\-+=;:,<>\\./\\?]{8,24}$";


    public ValidAccount(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public static void validAccountDto(String username, String email, String password) {
        validUserName(username);
        validEmail(email);
        validPassword(password);
    }

    public static void validUserName(String username) {
        boolean regex = Pattern.matches(USER_NAME_PATTERN, username);
        if (!regex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The username must be 5 to 20 characters in combination with letters and numbers",
                    new IllegalArgumentException()
            );
        }
    }

    public static void validEmail(String email) {
        boolean regex = Pattern.matches(EMAIL_PATTERN, email);
        if (!regex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "check email pattern",
                    new IllegalArgumentException()
            );
        }
    }

    public static void validPassword(String password) {
        boolean regex = Pattern.matches(PASSWORD_PATTERN, password);
        if (!regex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "check password pattern",
                    new IllegalArgumentException()
            );
        }
    }
}
