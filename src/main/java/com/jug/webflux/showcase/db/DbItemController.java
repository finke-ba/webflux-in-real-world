package com.jug.webflux.showcase.db;

import com.jug.webflux.showcase.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("db/items")
public class DbItemController {

    private final DbItemService dbItemService;

    public DbItemController(DbItemService dbItemService) {
        this.dbItemService = dbItemService;
    }

    @GetMapping
    public Mono<ResponseEntity<Item>> getItem(@RequestParam("id") String id) {
        return dbItemService.getItem(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
