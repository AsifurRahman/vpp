package com.asif.vpp.config.io;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final BatteryWebSocketHandler handler;

    public WebSocketConfig(BatteryWebSocketHandler handler) {
        this.handler = handler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/battery-ws")
                .setAllowedOriginPatterns("*"); // No SockJS
    }
}