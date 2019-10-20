package com.jug.webflux.showcase.security;

import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

public class ResponseUtil {

    public static <T> Mono<Void> putResponseIntoWebExchange(ServerWebExchange exchange,
                                                            ServerCodecConfigurer serverCodecConfigurer,
                                                            Mono<EntityResponse<T>> responseMono) {
        return responseMono.flatMap(entityResponse ->
                entityResponse.writeTo(exchange, new ServerResponse.Context() {
                    @NonNull
                    @Override
                    public List<HttpMessageWriter<?>> messageWriters() {
                        return serverCodecConfigurer.getWriters();
                    }

                    @NonNull
                    @Override
                    public List<ViewResolver> viewResolvers() {
                        return Collections.emptyList();
                    }
                }));
    }
}
