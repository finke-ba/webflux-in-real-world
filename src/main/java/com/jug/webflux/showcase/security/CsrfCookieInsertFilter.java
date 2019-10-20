package com.jug.webflux.showcase.security;

import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class CsrfCookieInsertFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        final Optional<Mono<CsrfToken>> csrfToken = Optional.ofNullable(exchange.getAttribute(CsrfToken.class.getName()));
        return csrfToken
                .orElse(Mono.empty())
                .then(chain.filter(exchange));
    }
}
