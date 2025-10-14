package com.odeyalo.sonata.gateway.filter;

import com.odeyalo.sonata.gateway.client.AccessTokenClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public final class AuthenticationGatewayFilter implements GatewayFilter {

    private final AccessTokenClient accessTokenClient;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationGatewayFilter.class);

    public AuthenticationGatewayFilter(final AccessTokenClient accessTokenClient) {
        this.accessTokenClient = accessTokenClient;
    }

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange,
                             final GatewayFilterChain chain) {
        final String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");

        if ( authorization == null || !authorization.startsWith("Bearer ") ) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        final String accessToken = authorization.substring(7);

        return accessTokenClient.validateToken(accessToken)
                .flatMap(body -> {
                    final String userId = body.getUserId();
                    exchange.getRequest().mutate().header("X-USER-ID", userId);
                    logger.trace("Set the X-USER-ID with value: {}", userId);
                    return chain.filter(exchange);
                })
                .onErrorResume(err -> {
                    logger.debug("Failed to validate access token: {}", accessToken);
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }
}
