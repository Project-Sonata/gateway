package com.odeyalo.sonata.gateway.config;

import com.odeyalo.sonata.gateway.filter.AuthenticationGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class RoutesConfiguration {

    @Bean
    public RouteLocator customRouteLocator(final RouteLocatorBuilder builder,
                                           final AuthenticationGatewayFilter authenticationGatewayFilter) {
        return builder.routes()
                .route("authentication", r -> r
                        .path("/v1/signup/email").and().method(HttpMethod.POST)
                        .or()
                        .path("/v1/signup/confirm").and().method(HttpMethod.POST)
                        .uri("lb://piano"))
                .route("profiles", r -> r
                        .path("/me")
                        .filters(f -> f.filter(authenticationGatewayFilter))
                        .uri("lb://profiles"))
                .build();
    }
}
