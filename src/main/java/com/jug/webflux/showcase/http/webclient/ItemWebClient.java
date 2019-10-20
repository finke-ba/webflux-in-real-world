package com.jug.webflux.showcase.http.webclient;

import com.jug.webflux.showcase.model.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ItemWebClient {

    private final WebClient webClient;

    public ItemWebClient(WebClient.Builder webClientBuilder, @Value("${items.service.uri}") String url) {
        this.webClient = webClientBuilder.baseUrl(url).build();
    }

    public Mono<Item> getItem(String id) {
        return this.webClient.get()
                .uri("/items/{id}", id)
                .retrieve()
                .bodyToMono(Item.class);
    }

}
