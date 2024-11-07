package com.example.demo.socket;

import com.example.demo.model.MessageEntity;
import com.example.demo.model.User;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        MessageEntity newMessage = new MessageEntity();
        newMessage.setContent(message.getPayload());
        messageRepository.save(newMessage);

        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public static void broadcastMessage(TextMessage message) throws Exception {
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(message);
            }
        }
    }
}