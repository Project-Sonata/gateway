package com.odeyalo.sonata.gateway.route;

import com.odeyalo.sonata.gateway.filter.AuthenticationGatewayFilter;
import org.springframework.stereotype.Component;

@Component
public final class ProfilesRouteRecord implements RouteRecord {
    private final AuthenticationGatewayFilter authenticationGatewayFilter;

    public ProfilesRouteRecord(final AuthenticationGatewayFilter authenticationGatewayFilter) {
        this.authenticationGatewayFilter = authenticationGatewayFilter;
    }

    @Override
    public Route getRoute() {
        return new Route(
                "profiles", r -> r
                .path("/v1/me")
                .filters(f -> f.filter(authenticationGatewayFilter))
                .uri("lb://profiles")
        );
    }
}
