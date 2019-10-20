package com.jug.webflux.showcase.http.feign;

import com.jug.webflux.showcase.model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class FeignItemService {

    private static final Logger LOG = LogManager.getLogger();

    private final FeignItemClient feignItemClient;

    public FeignItemService(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") FeignItemClient feignItemClient) {
        this.feignItemClient = feignItemClient;
    }

    public Mono<Item> getItem(String id) {
        return feignItemClient.getItem(id)
                .doOnNext(item -> LOG.info("Got item from blocking resource with Reactive Feign Client - {}", item))
                .subscribeOn(Schedulers.elastic());
    }

}
