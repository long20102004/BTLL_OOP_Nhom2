package com.example.demo.socket;

import com.example.demo.model.MessageEntity;
import com.example.demo.model.User;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OpenAIService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@AllArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private UserRepository userRepository;
    private static final Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private MessageRepository messageRepository;
    private OpenAIService openAIService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uri = Objects.requireNonNull(session.getUri()).toString();
        String userIdParam = uri.substring(uri.indexOf("user=") + 5);
        Integer userId = Integer.valueOf(userIdParam);
        sessions.put(userId, session);
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        JSONObject jsonMessage = new JSONObject(payload);
        Integer senderId = Integer.valueOf(jsonMessage.getString("sender"));
        Integer receiverId = Integer.valueOf(jsonMessage.getString("receiver"));
        String content = jsonMessage.getString("content");

        // Find sender and receiver
        User currentUser = userRepository.getReferenceById(senderId);
        User receiverUser = userRepository.getReferenceById(receiverId);

        // Save the user's message to the database
        MessageEntity newMessage = new MessageEntity();
        newMessage.setSender(currentUser);
        newMessage.setReceiver(receiverUser);
        newMessage.setContent(content);
        messageRepository.save(newMessage);


        // handle user chatting with bot
        if (receiverId == 0) { // Assuming ID 0 is for the bot
            String botResponse = openAIService.chatWithGPT(content);
            JSONObject botMessageJson = new JSONObject();
            botMessageJson.put("sender", 0); // Assuming 0 is the bot's ID
            botMessageJson.put("receiver", senderId);
            botMessageJson.put("content", botResponse);
            MessageEntity botMessage = new MessageEntity();
            botMessage.setContent(botResponse);
            botMessage.setSender(receiverUser);
            botMessage.setReceiver(currentUser);
            TextMessage botJson = new TextMessage(botMessageJson.toString());
            messageRepository.save(botMessage);
            session.sendMessage(botJson);
        } else {
            WebSocketSession receiverSession = sessions.get(receiverId);
            if (receiverSession != null && receiverSession.isOpen()) {
                receiverSession.sendMessage(message);
            }
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
    }

    public static void broadcastMessage(TextMessage message) throws Exception {
        for (WebSocketSession webSocketSession : sessions.values()) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(message);
            }
        }
    }
}