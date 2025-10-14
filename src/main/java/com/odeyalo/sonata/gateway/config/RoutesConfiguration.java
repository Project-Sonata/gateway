package com.odeyalo.sonata.gateway.config;

import com.odeyalo.sonata.gateway.route.RouteRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RoutesConfiguration {
    @Autowired
    List<RouteRecord> routeRecords;

    private final Logger logger = LoggerFactory.getLogger(RoutesConfiguration.class);

    @Bean
    public RouteLocator customRouteLocator(final RouteLocatorBuilder builder) {

        final RouteLocatorBuilder.Builder routeLocatorBuilder = builder.routes();

        routeRecords.forEach(record -> {

            logger.info("Applying {} to {}", record.getRoute(), routeLocatorBuilder);

            routeLocatorBuilder.route(
                    record.getRoute().name(),
                    record.getRoute().fn()
            );
        });

        logger.info("Successfully applied {} route records to {}", routeRecords.size(), routeLocatorBuilder);

        return routeLocatorBuilder.build();
    }
}
