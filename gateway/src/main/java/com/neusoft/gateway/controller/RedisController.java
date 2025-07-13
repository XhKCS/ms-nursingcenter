package com.neusoft.gateway.controller;

import com.neusoft.gateway.redisdao.RedisDao;
import com.neusoft.gateway.util.JWTTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redisController")
public class RedisController {
    @Autowired
    private RedisDao redisDao;

    @PostMapping("/save")
    public String save(@RequestBody Map<String, Object> request) {
        String key = (String) request.get("key");
        String val = (String) request.get("val");
        redisDao.set(key, val, JWTTool.calendarInterval, TimeUnit.SECONDS);
        return "ok";
    }

    @PostMapping("/saveWithTimeout")
    public String saveWithTimeout(@RequestBody Map<String, Object> request) {
        String key = (String) request.get("key");
        String val = (String) request.get("val");
        int timeout = (int) request.get("timeout");
        redisDao.set(key, val, timeout, TimeUnit.SECONDS);
        return "ok";
    }

    @PostMapping("/get")
    public String get(@RequestBody Map<String, Object> request) {
        String key = (String) request.get("key");
        String val = redisDao.get(key);
        return val;
    }

    @PostMapping("/del")
    public String delete(@RequestBody Map<String, Object> request) {
        String key = (String) request.get("key");
        redisDao.delete(key);
        return "ok";
    }
}
