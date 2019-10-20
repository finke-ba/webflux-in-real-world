package com.jug.webflux.showcase.db;

import com.jug.webflux.showcase.model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DbItemService {

    private static final Logger LOG = LogManager.getLogger();

    private final ItemRepository itemRepository;

    public DbItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Mono<Item> getItem(String id) {
        return itemRepository.findById(id)
                .map(itemDocument -> new Item(itemDocument.getId(), itemDocument.getName(), itemDocument.getCount(), itemDocument.getSource()))
                .doOnNext(item -> LOG.info("Got item from reactive DB - {}", item));
    }

}
