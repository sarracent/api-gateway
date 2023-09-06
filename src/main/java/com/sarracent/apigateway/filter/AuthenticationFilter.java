package com.sarracent.apigateway.filter;


import com.sarracent.apigateway.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter {
    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = null;
        if (validator.isSecured.test(exchange.getRequest())) {
            //header contains token and origin or not
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("missing header");
            }

            String authorizationHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            if (authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")) {
                authorizationHeader = authorizationHeader.substring(7);
            }

            try {

                jwtService.validateToken(String.valueOf(authorizationHeader));

            } catch (Exception e) {
                throw new RuntimeException("un authorized access to application");
            }
        }
        return chain.filter(exchange.mutate().request(request).build());
    }

}
