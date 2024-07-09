package com.atibo.backendspring.config;

import org.springframework.stereotype.Component;

import org.springframework.core.env.Environment;

@Component
public class ConfigEnv {

    private final Environment environment;

    public ConfigEnv(Environment environment) {
        this.environment = environment;
    }

    public String getLocalDir() {
        return environment.getProperty("spring.server.host_ip")+":"+environment.getProperty("spring.server.host_port");
    }
}
