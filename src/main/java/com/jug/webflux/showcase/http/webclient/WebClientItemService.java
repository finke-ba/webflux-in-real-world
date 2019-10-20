package com.jug.webflux.showcase.http.webclient;

import com.jug.webflux.showcase.model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WebClientItemService {

    private static final Logger LOG = LogManager.getLogger();

    private final ItemWebClient itemWebClient;

    public WebClientItemService(ItemWebClient itemWebClient) {
        this.itemWebClient = itemWebClient;
    }

    public Mono<Item> getItem(String id) {
        return itemWebClient
                .getItem(id)
                .doOnNext(item -> LOG.info("Got item from blocking resource with WebClient - {}", item));
    }

}
