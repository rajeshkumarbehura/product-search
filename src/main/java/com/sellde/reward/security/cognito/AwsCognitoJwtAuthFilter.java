package com.sellde.reward.security.cognito;

import com.sellde.reward.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class AwsCognitoJwtAuthFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var authHeaders = exchange.getRequest().getHeaders().get("Authorization");
        if (Utils.isNotNull(authHeaders)) {
            var authenticationToken = getAuthentication(exchange.getRequest());
            var value = ReactiveSecurityContextHolder.getContext()
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authenticationToken))
                    .map(ctx -> {
                        return ctx;
                    })
                    .then(chain.filter(exchange));
            return value;
        }
        return chain.filter(exchange);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(ServerHttpRequest request) {
        var userToken = Objects.requireNonNull(request.getHeaders().get("Authorization")).get(0);
        var userId = UUID.randomUUID().toString(); // temporary created, it can be fetched from jwt or db
        var auths = new ArrayList<GrantedAuthority>();
        var auth = new CustomGrantedAuthority();
        auths.add(auth);
        return new UsernamePasswordAuthenticationToken(new PrincipalUser(userId, userToken), userToken, auths);
    }

    @Data
    @AllArgsConstructor
    static
    class PrincipalUser {
        private String userName;
        private String token;
    }

    static class CustomGrantedAuthority implements GrantedAuthority {

        @Override
        public String getAuthority() {
            return "DEFAULT";
        }
    }
}