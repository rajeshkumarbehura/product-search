package com.sellde.reward.config;

import com.sellde.reward.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RestWebclient {

    @Value("${sellde.api-user-product}")
    private String userProductEndpoint;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    public Mono<WebClient> httpUserProductClient() {
        return authenticatedUser
                .getReactiveJwtToken()
                .map(token -> WebClient.builder()
                        .baseUrl(userProductEndpoint)
                        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build())
                .defaultIfEmpty(WebClient.builder()
                        .baseUrl(userProductEndpoint)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build());
    }

}
