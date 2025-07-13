package com.neusoft.coreserver.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("gateway") //要调用的远程服务的名称
public interface GatewayFeignClient {
    @PostMapping("/jwtController/createToken")
    String createToken(@RequestBody Map<String, Object> request);

    @PostMapping("/jwtController/parseToken")
    String parseToken(@RequestBody Map<String, Object> request);

    @PostMapping("/redisController/save")
    String save(@RequestBody Map<String, Object> request);

    @PostMapping("/redisController/get")
    String get(@RequestBody Map<String, Object> request);

    @PostMapping("/redisController/del")
    String delete(@RequestBody Map<String, Object> request);
}
