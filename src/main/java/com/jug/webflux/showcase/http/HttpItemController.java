package com.jug.webflux.showcase.http;

import com.jug.webflux.showcase.http.webclient.WebClientItemService;
import com.jug.webflux.showcase.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("http/items")
public class HttpItemController {

    private final WebClientItemService webClientItemService;

    public HttpItemController(WebClientItemService webClientItemService) {
        this.webClientItemService = webClientItemService;
    }

    @GetMapping("webclient")
    public Mono<ResponseEntity<Item>> getItemReactive(@RequestParam("id") String id) {
        return webClientItemService.getItem(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
