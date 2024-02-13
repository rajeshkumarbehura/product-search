package com.sellde.reward.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.sellde.reward.util.Utils.emptyString;
import static com.sellde.reward.util.Utils.isNotNull;

@Component
public class AuthenticatedUser {

    public Mono<String> getReactiveJwtToken() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> {
                    var auth = ctx.getAuthentication();
                    return isNotNull(auth) ?
                            ((Jwt) auth.getCredentials()).getTokenValue()
                            : emptyString();
                });
    }

    public Mono<String> getReactiveJwtToken(StringBuffer jwtToken) {
        return getReactiveJwtToken()
                .map(token -> jwtToken.append(token).toString());
    }
}
