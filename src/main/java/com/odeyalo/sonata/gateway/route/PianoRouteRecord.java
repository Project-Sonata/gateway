package com.odeyalo.sonata.gateway.route;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public final class PianoRouteRecord implements RouteRecord {

    @Override
    public Route getRoute() {
        return new Route(
                "authentication", r -> r
                .path("/v1/signup/email").and().method(HttpMethod.POST)
                .or()
                .path("/v1/signup/confirm").and().method(HttpMethod.POST)
                .uri("lb://piano")
        );
    }
}
