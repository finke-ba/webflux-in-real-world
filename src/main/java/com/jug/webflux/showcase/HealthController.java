package com.jug.webflux.showcase;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("health")
public class HealthController {

    @GetMapping
    public Mono<ResponseEntity<OkHealth>> getHealth() {
        return Mono.just(ResponseEntity.ok(new OkHealth()));
    }

    @Data
    @NoArgsConstructor
    private static class OkHealth {
        private final String status = "Ok";
    }
}
