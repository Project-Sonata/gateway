package com.odeyalo.sonata.gateway.route;

import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;

import java.util.function.Function;

public interface RouteRecord {

    Route getRoute();

    record Route(
            String name,
            Function<PredicateSpec, Buildable<org.springframework.cloud.gateway.route.Route>> fn
    ) {}
}
