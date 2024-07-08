package com.atibo.backendspring.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    private int count = 0;

    @GetMapping("/health")
    public String health() {
        count++;
        if (count > 5) {
            throw new RuntimeException();
        }
        return "OK";
    }
}
