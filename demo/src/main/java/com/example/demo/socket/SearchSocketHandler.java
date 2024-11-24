package com.example.demo.socket;

import com.example.demo.model.PostEntity;
import com.example.demo.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@AllArgsConstructor
public class SearchSocketHandler extends TextWebSocketHandler {
    private PostRepository postRepository;
    private static final Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uri = Objects.requireNonNull(session.getUri()).toString();
        String userIdParam = uri.substring(uri.indexOf("user=") + 5);
        Integer userId = Integer.valueOf(userIdParam);
        sessions.put(userId, session);
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) throws Exception {
        if (session.isOpen()) {
            String payload = message.getPayload();
            JSONObject jsonMessage = new JSONObject(payload);
            String query = jsonMessage.getString("query");
            List<PostEntity> postEntityList = new ArrayList<>();
            if (!query.trim().isEmpty()) {
                postEntityList = postRepository.findAllByQuery(query);
            }

            JSONObject response = new JSONObject();
            response.put("posts", postEntityList);
            session.sendMessage(new TextMessage(response.toString()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
    }

}