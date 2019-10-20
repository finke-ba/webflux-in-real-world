package com.jug.webflux.showcase.security;

import org.springframework.http.HttpMethod;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

public class LoginWebFilter extends AuthenticationWebFilter {

    public LoginWebFilter(ReactiveAuthenticationManager authenticationManager,
                          ServerCodecConfigurer serverCodecConfigurer,
                          ServerSecurityContextRepository serverSecurityContextRepository) {
        super(authenticationManager);

        setRequiresAuthenticationMatcher(
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login")
        );

        setServerAuthenticationConverter(new UserDataServerAuthenticationConverter(serverCodecConfigurer));

        setAuthenticationSuccessHandler(new UserDataAuthSuccessHandler(serverCodecConfigurer));

        setSecurityContextRepository(serverSecurityContextRepository);
    }
}
