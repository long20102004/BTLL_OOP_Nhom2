package com.example.demo.socket;

import com.example.demo.model.MessageEntity;
import com.example.demo.model.User;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private UserRepository userRepository;

    private static final Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uri = Objects.requireNonNull(session.getUri()).toString();
        String userIdParam = uri.substring(uri.indexOf("user=") + 5);
        Integer userId = Integer.valueOf(userIdParam);
        sessions.put(userId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println(payload);
        JSONObject jsonMessage = new JSONObject(payload);
        Integer senderId = Integer.valueOf(jsonMessage.getString("sender"));
        Integer receiverId = Integer.valueOf(jsonMessage.getString("receiver"));
        String content = jsonMessage.getString("content");
        User currentUser = userRepository.findById(senderId).get();
        User receiverUser = userRepository.findById(receiverId).get();
        MessageEntity newMessage = new MessageEntity();
        newMessage.setSender(currentUser);
        newMessage.setReceiver(receiverUser);
        newMessage.setContent(content);
        messageRepository.save(newMessage);

        WebSocketSession receiverSession = sessions.get(receiverId);
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public static void broadcastMessage(TextMessage message) throws Exception {
        for (WebSocketSession webSocketSession : sessions.values()) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(message);
            }
        }
    }
}