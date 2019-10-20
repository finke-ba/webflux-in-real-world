package com.jug.webflux.showcase.security;

import com.jug.webflux.showcase.security.model.LoginRequest;
import com.jug.webflux.showcase.security.model.LoginResponse;
import feign.RequestLine;
import reactor.core.publisher.Mono;

public interface AuthClient {
    @RequestLine("POST /login")
    Mono<LoginResponse> login(LoginRequest loginRequest);
}
