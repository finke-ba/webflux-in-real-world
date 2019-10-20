package com.jug.webflux.showcase.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import reactivefeign.webclient.WebReactiveFeign;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ReactiveAuthenticationManager authenticationManager,
                                                         ServerCodecConfigurer serverCodecConfigurer,
                                                         ServerSecurityContextRepository serverSecurityContextRepository,
                                                         ServerAuthenticationEntryPoint entryPoint) {
        return http
                .addFilterAt(new LoginWebFilter(authenticationManager, serverCodecConfigurer, serverSecurityContextRepository), SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .logout()
                .and()
                .authorizeExchange()
                .pathMatchers("/login").permitAll()
                .anyExchange().authenticated()
                .and()
                .csrf().csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .build();
    }

    @Bean
    ServerAuthenticationEntryPoint entryPoint() {
        return new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    @Bean
    ReactiveAuthenticationManager authenticationManager(AuthClient authClient) {
        return new CustomAuthenticationManager(authClient);
    }

    @Bean
    public ServerSecurityContextRepository serverSecurityContextRepository() {
        return new WebSessionServerSecurityContextRepository();
    }

    @Bean
    AuthClient authClient(@Value("${items.service.uri}") String url) {
        return WebReactiveFeign
                .<AuthClient>builder()
                .target(AuthClient.class, url);
    }

}
