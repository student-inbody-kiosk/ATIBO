package com.atibo.backendspring.accounts.application;

import java.util.UUID;
import java.util.regex.Pattern;

import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.repository.AccountRepository;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Configuration
public class ValidAccount {

    private final AccountRepository accountRepository;

    private static final String USER_NAME_PATTERN = "^(?=.*[A-Za-z])([A-Za-z\\d]{5,20}$)";      //한글과 영어 조합 5~20자. 최소 1개의 문자 포함
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";    // email 형식
    //문자, 숫자, 특수문자 조합 8~24자. 각각 1개의 문자 포함
    public static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\\(\\)\\[\\]\\{\\}\\|\\\\`~!@#\\$%\\^&\\*\\-+=;:,<>\\./\\?])[A-Za-z\\d\\(\\)\\[\\]\\{\\}\\|\\\\`~!@#\\$%\\^&\\*\\-+=;:,<>\\./\\?]{8,24}$";
    public static final String PSQ = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#\\$%^\\&*])([A-Za-z\\d!@#\\$%^\\&*]{8,24})$";

    private static final String NAME_PATTERN = "^[가-힣]{2,5}$";
    private static final String COMMENT_PATTERN = "^.{10,100}$";

    public ValidAccount(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public static void validAccount(AccountDto.RequestDto account) {
        String username = account.getUsername();
        String name = account.getName();
        String email = account.getEmail();
        String password = account.getPassword();
        String comment = account.getComment().trim();

        validUserName(username);
        validName(name);
        validEmail(email);
        validComment(comment);
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
                    "Enter a valid email address.",
                    new IllegalArgumentException()
            );
        }
    }

    public static void validPassword(String password) {
        boolean regex = Pattern.matches(PASSWORD_PATTERN, password);
        if (!regex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "비밀번호 양식이 맞지 않습니다.",
                    new IllegalArgumentException()
            );
        }
    }

    public static void validName(String name) {
        boolean regex = Pattern.matches(NAME_PATTERN, name);
        if (!regex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The name must be written in 2-5 Korean characters,\"\n\"Ensure this field has no more than 5 characters.",
                    new IllegalArgumentException()
            );
        }
    }

    public static void validComment(String comment) {
        boolean regex = Pattern.matches(COMMENT_PATTERN, comment);
        if (!regex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The comment must be written 10-100 characters",
                    new IllegalArgumentException()
            );
        }
    }

    public void existedUserName(String username) {
        boolean exist = accountRepository.existsByUsername(username);
        if (!exist) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Check username",
                    new IllegalArgumentException()
            );
        }
    }

    public void existedUserId(UUID userId) {
        boolean exist = accountRepository.existsById(userId);
        if (!exist) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Check userId",
                    new IllegalArgumentException()
            );
        }
    }

    public void existedEmail(String email) {
        boolean exist = accountRepository.existsByEmail(email);
        if (!exist) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Check email",
                    new IllegalArgumentException()
            );
        }
    }
}
