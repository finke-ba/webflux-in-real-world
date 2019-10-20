package com.jug.webflux.showcase.security;

import com.jug.webflux.showcase.security.model.LoginRequest;
import com.jug.webflux.showcase.security.model.UserRequestAuthentication;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;

public class UserDataServerAuthenticationConverter implements ServerAuthenticationConverter {

    private final ServerCodecConfigurer serverCodecConfigurer;

    UserDataServerAuthenticationConverter(ServerCodecConfigurer serverCodecConfigurer) {
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return serverCodecConfigurer.getReaders().stream()
                .filter(reader -> reader.canRead(ResolvableType.forClass(LoginRequest.class), MediaType.APPLICATION_JSON))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No JSON reader for LoginRequest"))
                .readMono(ResolvableType.forClass(LoginRequest.class), exchange.getRequest(), Collections.emptyMap())
                .cast(LoginRequest.class)
                .map(loginRequest -> new UserRequestAuthentication(loginRequest.getName(), loginRequest.getPassword()));
    }
}
