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
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactivefeign.webclient.WebReactiveFeign;

import java.util.Arrays;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${cors.allowed.origins:}")
    private String[] allowedCorsOrigins;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ReactiveAuthenticationManager authenticationManager,
                                                         ServerCodecConfigurer serverCodecConfigurer,
                                                         ServerSecurityContextRepository serverSecurityContextRepository,
                                                         ServerAuthenticationEntryPoint entryPoint,
                                                         CorsConfigurationSource corsConfigurationSource,
                                                         ServerCsrfTokenRepository cookieServerCsrfTokenRepository) {
        return http
                .addFilterAt(new LoginWebFilter(authenticationManager, serverCodecConfigurer, serverSecurityContextRepository), SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(new CsrfCookieInsertFilter(), SecurityWebFiltersOrder.REACTOR_CONTEXT)
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .logout()
                .and()
                .authorizeExchange()
                .pathMatchers("/login", "/health").permitAll()
                .anyExchange().authenticated()
                .and()
                .csrf().csrfTokenRepository(cookieServerCsrfTokenRepository)
                .and()
                .cors().configurationSource(corsConfigurationSource)
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
    ServerSecurityContextRepository serverSecurityContextRepository() {
        return new WebSessionServerSecurityContextRepository();
    }

    @Bean
    AuthClient authClient(@Value("${items.service.uri}") String url) {
        return WebReactiveFeign
                .<AuthClient>builder()
                .target(AuthClient.class, url);
    }

    @Bean
    ServerCsrfTokenRepository cookieServerCsrfTokenRepository() {
        final CookieServerCsrfTokenRepository csrfTokenRepository = CookieServerCsrfTokenRepository.withHttpOnlyFalse();
        csrfTokenRepository.setCookieHttpOnly(false);
        return csrfTokenRepository;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.setAllowedOrigins(Arrays.asList(allowedCorsOrigins));

        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("PUT");
        corsConfig.addAllowedMethod("PATCH");
        corsConfig.addAllowedMethod("DELETE");
        corsConfig.addAllowedMethod("HEAD");
        corsConfig.addAllowedMethod("OPTIONS");

        corsConfig.addAllowedHeader("Content-Type");
        corsConfig.addAllowedHeader("X-XSRF-TOKEN");

        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }

}
