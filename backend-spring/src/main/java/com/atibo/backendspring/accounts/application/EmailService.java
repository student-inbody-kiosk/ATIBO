package com.atibo.backendspring.accounts.application;

import com.atibo.backendspring.accounts.domain.Account;
import com.atibo.backendspring.accounts.dto.AccountDto;
import com.atibo.backendspring.accounts.repository.AccountRepository;
import com.atibo.backendspring.util.PasswordGenerator;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private static final String EMAIL_SUBJECT = "[ATIBO] 비밀번호 초기화";
    private static final String EMAIL_BODY = "비밀번호가 초기화 되었습니다";
    private static final String EMAIL_FROM = "atibo5268@gmail.com";

    private final JavaMailSender javaMailSender;
    private final ValidAccount validAccount;
    private final AccountRepository accountRepository;
    private final PasswordGenerator passwordGenerator;

    public void setMail(AccountDto.resetPasswordDto request) {
        // TODO: account service 로 기능 분리할 필요가 있음
        String username = request.getUsername();
        String email = request.getEmail();
        Account account = accountRepository.findByUsername(username);
        validAccount.existedUserName(username);
        validAccount.existedEmail(email);
        String newPassword = passwordGenerator.generatePassword();
        account.changePassword(newPassword);
        sendMail(email, EMAIL_SUBJECT, EMAIL_BODY + "[ " + newPassword + " ]");
    }

    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(EMAIL_FROM);
        javaMailSender.send(message);
    }
}
