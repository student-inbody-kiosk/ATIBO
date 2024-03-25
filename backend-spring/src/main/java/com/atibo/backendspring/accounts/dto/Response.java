package com.atibo.backendspring.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Response {
    @JsonProperty("message")
    String message;

    public Response() {
    }

    public Response(String message) {

        this.message = message;
    }
}
