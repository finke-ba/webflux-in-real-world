package com.jug.webflux.showcase.security;

import com.jug.webflux.showcase.security.model.LoginRequest;
import com.jug.webflux.showcase.security.model.UserRequestAuthentication;
import com.jug.webflux.showcase.security.model.UserResponseAuthentication;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class CustomAuthenticationManager implements ReactiveAuthenticationManager {

    private final AuthClient authClient;

    public CustomAuthenticationManager(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        final String userName = ((UserRequestAuthentication) authentication).getUserName();
        final String password = ((UserRequestAuthentication) authentication).getPassword();

        return authClient.login(new LoginRequest(userName, password))
                .map(loginResponse -> new UserResponseAuthentication(loginResponse.getId(), loginResponse.getToken()));
    }
}
