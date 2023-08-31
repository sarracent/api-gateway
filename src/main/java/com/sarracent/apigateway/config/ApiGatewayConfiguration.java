package com.sarracent.apigateway.config;

import com.sarracent.apigateway.constants.AppConstants;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(AppConstants.STUDENT_SERVICE_KEY,
                        r -> r.path("/api/student-service/**")
                                .filters(f -> f.stripPrefix(2)).uri("http://localhost:8081"))
                .route(AppConstants.COURSE_SERVICE_KEY,
                        r -> r.path("/api/course-service/**")
                                .filters(f -> f.stripPrefix(2)).uri("http://localhost:8082"))
                .build();
    }
}
