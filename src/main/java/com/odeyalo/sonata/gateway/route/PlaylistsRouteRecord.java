package com.odeyalo.sonata.gateway.route;

import com.odeyalo.sonata.gateway.filter.AuthenticationGatewayFilter;
import org.springframework.stereotype.Component;

@Component
public final class PlaylistsRouteRecord implements RouteRecord {
    private final AuthenticationGatewayFilter authenticationGatewayFilter;

    public PlaylistsRouteRecord(final AuthenticationGatewayFilter authenticationGatewayFilter) {
        this.authenticationGatewayFilter = authenticationGatewayFilter;
    }

    @Override
    public Route getRoute() {
        return new Route(
                "sonata-playlists", r -> r
                .path("/v1/me/playlists", "/v1/me/playlists/*")
                .filters(
                        f -> f.filter(authenticationGatewayFilter)
                                .rewritePath("/v1/me", "/v1/")
                )
                .uri("lb://playlists")
        );
    }
}
