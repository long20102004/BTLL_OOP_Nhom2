// File: src/main/java/com/example/demo/config/WebSocketConfig.java
package com.example.demo.socket;

import com.example.demo.repository.CommentRepository;
import com.example.demo.socket.ChatWebSocketHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatWebSocketHandler chatWebSocketHandler;
    private final SearchSocketHandler searchSocketHandler;
    private final CommentSocketHandler commentSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/ws/chat").setAllowedOrigins("*");
        registry.addHandler(searchSocketHandler, "/ws/se").setAllowedOrigins("*");
        registry.addHandler(commentSocketHandler, "ws/cmt").setAllowedOrigins("*");
    }
}