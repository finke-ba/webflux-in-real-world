package com.jug.webflux.showcase.security;

import com.jug.webflux.showcase.security.model.LoginResponse;
import com.jug.webflux.showcase.security.model.UserResponseAuthentication;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.reactive.function.server.EntityResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class UserDataAuthSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final ServerCodecConfigurer serverCodecConfigurer;

    UserDataAuthSuccessHandler(ServerCodecConfigurer serverCodecConfigurer) {
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        final LoginResponse response = new LoginResponse(
                ((UserResponseAuthentication) authentication).getId(),
                ((UserResponseAuthentication) authentication).getToken()
        );

        final Mono<EntityResponse<LoginResponse>> responseMono = EntityResponse.fromObject(response)
                .contentType(APPLICATION_JSON)
                .build();

        return ResponseUtil.putResponseIntoWebExchange(webFilterExchange.getExchange(), serverCodecConfigurer, responseMono);
    }
}
