package com.sellde.reward.security;

import com.sellde.reward.security.cognito.AwsCognitoJwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static com.sellde.reward.util.Constant.ACTIVE_PROFILE;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private Environment environment;


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        ServerHttpSecurity.AuthorizeExchangeSpec
                authExchangeSpec = http.requestCache().disable().
                csrf().disable()
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .addFilterAt(new AwsCognitoJwtAuthFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
                .authorizeExchange();

        String activeProfile = null;
        if (environment.getActiveProfiles().length > 0) {
            activeProfile = environment.getActiveProfiles()[0];
        }
        if (activeProfile == null) {
            activeProfile = environment.getProperty(ACTIVE_PROFILE);
        }

        authExchangeSpec
                .pathMatchers(HttpMethod.OPTIONS, "/v1/**").permitAll()
                .pathMatchers("/v1/**").authenticated()
                // swagger related, in prod we can block it
                .pathMatchers(HttpMethod.GET, "/**").permitAll();
        ;
        return http.build();
    }
}