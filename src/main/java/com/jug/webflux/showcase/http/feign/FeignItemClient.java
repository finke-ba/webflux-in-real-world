package com.jug.webflux.showcase.http.feign;

import com.jug.webflux.showcase.model.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "items", url = "${items.service.uri}")
public interface FeignItemClient {

    @GetMapping("/items/{id}")
    Item getItem(@RequestParam("id") String id);

}
