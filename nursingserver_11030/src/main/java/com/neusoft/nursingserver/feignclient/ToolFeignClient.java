package com.neusoft.nursingserver.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("tool-server") //要调用的远程服务的名称
public interface ToolFeignClient {
    @PostMapping("/webSocketController/sendAll")
    void sendAllMessage(@RequestBody Map<String, Object> request);

    @PostMapping("/webSocketController/sendOne")
    void sendOneMessage(@RequestBody Map<String, Object> request);

    @PostMapping("/webSocketController/sendMore")
    void sendMoreMessage(@RequestBody Map<String, Object> request);
}
