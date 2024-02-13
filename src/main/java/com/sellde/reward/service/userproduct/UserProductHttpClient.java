package com.sellde.reward.service.userproduct;

import com.sellde.reward.config.RestWebclient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UserProductHttpClient {

    @Autowired
    private RestWebclient restWebclient;

    public Mono<String> get(String apiPath, Object... uriVariables) {
        return restWebclient
                .httpUserProductClient()
                .flatMap(webClient -> webClient.get()
                        .uri(apiPath, uriVariables)
                        .exchangeToMono(clientResponse -> {
                            return clientResponse.bodyToMono(String.class);
                        })
                );
    }

    public <T> Mono<T> get(String apiPath, Class<T> t, Object... uriVariables) {
        return restWebclient
                .httpUserProductClient()
                .flatMap(webClient -> webClient.get()
                        .uri(apiPath, uriVariables)
                        .exchangeToMono(clientResponse -> {
                            return clientResponse.bodyToMono(t);
                        })
                );
    }

}
