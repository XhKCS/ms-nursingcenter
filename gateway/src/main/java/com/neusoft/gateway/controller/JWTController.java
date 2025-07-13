package com.neusoft.gateway.controller;

import com.neusoft.gateway.util.JWTTool;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/jwtController")
public class JWTController {
    @PostMapping("/createToken")
    public String createToken(@RequestBody Map<String, Object> request) {
        String json = (String) request.get("json");
        return JWTTool.createToken(json);
    }

    @PostMapping("/parseToken")
    public String parseToken(@RequestBody Map<String, Object> request) {
        String token = (String) request.get("token");
        return JWTTool.parseToken(token);
    }
}
