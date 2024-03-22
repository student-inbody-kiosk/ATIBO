package com.atibo.backendspring.accounts.dto;

import lombok.Getter;

@Getter
public class Response {

    String message;

    public Response() {
    }

    public Response(String message) {

        this.message = message;
    }
}
