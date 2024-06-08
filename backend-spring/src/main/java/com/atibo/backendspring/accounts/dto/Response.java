package com.atibo.backendspring.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Response {
    @JsonProperty("message")
    String message;

    public Response(String message) {

        this.message = message;
    }

    public static class emailResponse {
        @JsonProperty("email")
        String email;

        public emailResponse(String email) {
            this.email = email;
        }
    }

    public static class isActiveResponse {
        @JsonProperty("isWaiting")
        Boolean isWaiting;

        public isActiveResponse(Boolean b) {
            this.isWaiting = b;
        }
    }
}
