package com.atibo.backendspring.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class PasswordGenerator {

    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = UPPER_CASE.toLowerCase();
    private static final String NUMBERS = "0123456789";
    private static final int LENGTH = 8;
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+";
    private static final String ALL_CHARS = UPPER_CASE + LOWER_CASE + NUMBERS + SPECIAL_CHARS;
    private static final SecureRandom random = new SecureRandom();

    public String generatePassword() {
        // 각 카테고리에서 최소 하나의 문자를 포함시키기 위한 로직
        StringBuilder password = new StringBuilder(LENGTH);

        // 대문자, 소문자, 숫자, 특수 문자 각각에서 하나씩 추가
        password.append(UPPER_CASE.charAt(random.nextInt(UPPER_CASE.length())));
        password.append(LOWER_CASE.charAt(random.nextInt(LOWER_CASE.length())));
        password.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

        // 나머지 길이를 채우기
        for (int i = 4; i < LENGTH; i++) {
            password.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        // 문자 위치 섞기
        char[] pwdArray = password.toString().toCharArray();
        for (int i = 0; i < pwdArray.length; i++) {
            int randomIndex = random.nextInt(pwdArray.length);
            char temp = pwdArray[i];
            pwdArray[i] = pwdArray[randomIndex];
            pwdArray[randomIndex] = temp;
        }

        return new String(pwdArray);
    }
}
