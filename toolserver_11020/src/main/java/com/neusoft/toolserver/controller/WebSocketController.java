package com.neusoft.toolserver.controller;

import com.neusoft.toolserver.util.WebSocket;
import jakarta.annotation.Resource;
import jakarta.websocket.Session;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/webSocketController")
public class WebSocketController {
    @Resource
    private WebSocket webSocket;

    // 此为广播消息
    @PostMapping("/sendAll")
    public void sendAllMessage(@RequestBody Map<String, Object> request) {
        String message = (String) request.get("message");
        webSocket.sendAllMessage(message);
    }

    // 此为单点消息
    @PostMapping("/sendOne")
    public void sendOneMessage(@RequestBody Map<String, Object> request) {
        String userId = (String) request.get("userId");
        String message = (String) request.get("message");
        webSocket.sendOneMessage(userId, message);
    }

    // 此为单点消息（多人）
    @PostMapping("/sendMore")
    public void sendMoreMessage(@RequestBody Map<String, Object> request) {
        List<String> userIdList = (List<String>) request.get("userIdList");
        String message = (String) request.get("message");
        webSocket.sendMoreMessage(userIdList, message);
    }
}
