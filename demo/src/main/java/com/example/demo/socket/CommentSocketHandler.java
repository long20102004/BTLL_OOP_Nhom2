package com.example.demo.socket;

import com.example.demo.model.CommentEntity;
import com.example.demo.model.PostEntity;
import com.example.demo.model.ReplyEntity;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.ReplyRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class CommentSocketHandler extends TextWebSocketHandler {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private ReplyRepository replyRepository;
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uri = Objects.requireNonNull(session.getUri()).toString();
        String postIdParam = uri.substring(uri.indexOf("post=") + 5);
        Integer postId = Integer.valueOf(postIdParam);
        sessions.put(session.getId(), session);
        session.getAttributes().put("postId", postId);
    }

    public static void broadcastMessage(TextMessage message, Integer postId) throws Exception {
        for (WebSocketSession webSocketSession : sessions.values()) {
            if (webSocketSession.isOpen() && postId.equals(webSocketSession.getAttributes().get("postId"))) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) throws Exception {
        if (session.isOpen()) {
            String payload = message.getPayload();
            JSONObject jsonMessage = new JSONObject(payload);
            Integer postId = Integer.valueOf(jsonMessage.getString("post-id"));
            Integer userId = Integer.valueOf(jsonMessage.getString("user-id"));
            String commentContent = jsonMessage.getString("content");
            String type = jsonMessage.getString("type");
            if (type.equals("reply")) {
                Integer commentId = Integer.valueOf(jsonMessage.getString("comment-id"));
                CommentEntity comment = commentRepository.findById(commentId).get();
                ReplyEntity replyEntity = new ReplyEntity();
                replyEntity.setContent(commentContent);
                replyEntity.setCommentReply(comment);
                replyEntity.setUserReply(userRepository.findById(userId).get());
                replyRepository.save(replyEntity);
            } else {
                CommentEntity comment = new CommentEntity();
                comment.setPostComment(postRepository.findById(postId).get());
                comment.setUserComment(userRepository.findById(userId).get());
                comment.setContent(commentContent);
                commentRepository.save(comment);
            }
            broadcastMessage(message, postId);

        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
    }


}