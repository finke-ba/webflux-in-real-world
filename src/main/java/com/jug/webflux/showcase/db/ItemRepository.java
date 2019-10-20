package com.jug.webflux.showcase.db;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ItemRepository extends ReactiveMongoRepository<ItemDocument, String> {
}
