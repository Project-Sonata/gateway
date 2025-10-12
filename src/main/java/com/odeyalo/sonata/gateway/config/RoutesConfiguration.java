package com.odeyalo.sonata.gateway.config;

import com.odeyalo.sonata.gateway.filter.AuthenticationGatewayFilter;
import com.odeyalo.sonata.gateway.route.RouteRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.util.List;

@Configuration
public class RoutesConfiguration {
    @Autowired
    List<RouteRecord> routeRecords;

    @Bean
    public RouteLocator customRouteLocator(final RouteLocatorBuilder builder,
                                           final AuthenticationGatewayFilter authenticationGatewayFilter) {

        final RouteLocatorBuilder.Builder routeLocatorBuilder = builder.routes();

        routeRecords.forEach(record -> {
            routeLocatorBuilder.route(
                    record.getRoute().name(),
                    record.getRoute().fn()
            );
        });


        return builder.routes()
                .route("profiles", r -> r
                        .path("/v1/me")
                        .filters(f -> f.filter(authenticationGatewayFilter))
                        .uri("lb://profiles"))
                .build();
    }
}
