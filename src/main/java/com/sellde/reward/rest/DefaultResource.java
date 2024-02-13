package com.sellde.reward.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
@RequestMapping
public class DefaultResource {

    @GetMapping("/default/health-check")
    @ResponseStatus(HttpStatus.OK)
    public Mono<HashMap<String, String>> getHealthCheck() {
        var map = new HashMap<String, String>();
        map.put("status", "success");
        map.put("value", "health is good");
        return Mono.just(map);
    }

    @GetMapping("/api/v2/index")
    @ResponseStatus(HttpStatus.OK)
    public Mono<HashMap<String, String>> getHealthCheckIndex() {
        return getHealthCheck();
    }
}
