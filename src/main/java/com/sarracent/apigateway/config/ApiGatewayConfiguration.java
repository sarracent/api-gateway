package com.sarracent.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/auth/register")
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/auth/token")
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/auth/validate/**")
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/api/prices/**")
                        .uri("http://localhost:8080"))
                .build();
    }
}
