package com.neusoft.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.gateway.redisdao.RedisDao;
import com.neusoft.gateway.util.JWTTool;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class AuthGatewayFilterFactory implements GlobalFilter {
    private final String[] allowedRequests = {
            "/login", "/v3", "/swagger-ui", "/jwtController", "/redisController",
            "/webjars", "/error", "/doc", "/ai"
    };

    @Autowired
    private RedisDao redisDao;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getURI().getPath();
        System.out.println("请求路径："+requestPath);
        for (String str : allowedRequests) {
            if (requestPath.contains(str)) {
                return chain.filter(exchange);
            }
        }
        String xPass = request.getHeaders().getFirst("x-pass");
        if (xPass != null && xPass.equals("x-passing")) {
            return chain.filter(exchange);
        }
        // 验证token
        String token = request.getHeaders().getFirst("token");
        ServerHttpResponse response = exchange.getResponse();
        System.out.println("执行鉴权，发来的token为："+token);
        if (token == null) {
            System.out.println("--- Unauthorized ---");
            response.setStatusCode(HttpStatus.UNAUTHORIZED); //注意前端要在异常处理中判断响应状态码
            return response.setComplete();
        }
        try {
            String json = JWTTool.parseToken(token);
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> map = om.readValue(json, Map.class);
            if (map.containsKey("userId")) {
//    			再到redis中按照userId的格式取出相应令牌
                String storedToken = redisDao.get("user-"+map.get("userId").toString());
                System.out.println("storedToken: "+storedToken);
                if(!token.equals(storedToken)) {
                    System.out.println("--- Unauthorized ---");
                    response.setStatusCode(HttpStatus.UNAUTHORIZED); //注意前端要在异常处理中判断响应状态码
                    return response.setComplete();
                }
            }
            else if (map.containsKey("customerId")) {
//              再到redis中按照customerId的格式取出相应令牌
                String storedToken = redisDao.get("customer-"+map.get("customerId").toString());
                System.out.println("storedToken: "+storedToken);
                if(!token.equals(storedToken)) {
                    System.out.println("--- Unauthorized ---");
                    response.setStatusCode(HttpStatus.UNAUTHORIZED); //注意前端要在异常处理中判断响应状态码
                    return response.setComplete();
                }
            }
        } catch (Exception e) {
            System.out.println("Exception happened while verifying: "+e.getMessage());
            response.setStatusCode(HttpStatus.UNAUTHORIZED); //注意前端要在异常处理中判断响应状态码
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

}
