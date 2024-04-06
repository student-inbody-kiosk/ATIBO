package com.atibo.backendspring.accounts.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailMessage {

    private String to;
    private final String subjects = "[ATIBO] 비밀번호 초기화";
//  TODO: 비밀번호 임의로 초기화인지 랜덤값 인지 확인하기
    private final String message = "비밀번호가 초기화 되었습니다 [1q2w3e4r!]";
}
