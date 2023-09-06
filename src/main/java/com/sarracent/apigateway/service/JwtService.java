package com.sarracent.apigateway.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    void validateToken(String token);
}
