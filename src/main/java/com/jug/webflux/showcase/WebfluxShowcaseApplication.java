package com.jug.webflux.showcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients
public class WebfluxShowcaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxShowcaseApplication.class, args);
    }

}
