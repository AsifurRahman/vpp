package com.asif.vpp.config.io;

import com.asif.vpp.feature.batterysave.BatterySaveStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BatteryWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendStatusToSession(BatterySaveStatus status) {
        WebSocketSession session = sessions.get(status.getRequestId());
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(status)));
                System.out.println("Sending status to session: " + status.getRequestId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String requestId = getRequestId(session);
        sessions.put(requestId, session);
        System.out.println("Connection established for requestId: " + requestId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String requestId = getRequestId(session);
        if (requestId != null) {
            sessions.remove(requestId);
        }
        System.out.println("Connection closed for requestId: " + requestId);
    }

    private String getRequestId(WebSocketSession session) {
        String requestId =  session.getUri().getQuery().replace("requestId=", "");
        if (requestId.isEmpty()) {
            throw new IllegalArgumentException("Request ID is missing in the WebSocket URI");
        }
        return requestId;
    }
}
