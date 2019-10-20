package com.jug.webflux.showcase;

import com.jug.webflux.showcase.model.Item;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("stream")
public class StreamingController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Item> getItem() {
        final Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(5));

        final Stream<Item> itemStream = Stream.generate(() ->
                new Item(UUID.randomUUID().toString(), "Item 1", "1", "stream"));

        return Flux
                .zip(Flux.fromStream(itemStream), durationFlux)
                .map(Tuple2::getT1);
    }
}
