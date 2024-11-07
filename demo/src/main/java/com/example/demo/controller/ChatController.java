package com.example.demo.controller;

import com.example.demo.model.MessageEntity;
import com.example.demo.repository.MessageRepository;
import com.example.demo.socket.ChatWebSocketHandler;
import com.example.demo.util.InputSanitizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping("/chat")
    public String getChatPage(Model model) {
        List<MessageEntity> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "message";
    }

    @PostMapping("/chat")
    public String postMessage(@RequestParam("message") String message,
                              @RequestParam("image") MultipartFile image,
                              RedirectAttributes redirectAttributes) {
        try {
            String sanitizedMessage = InputSanitizer.sanitize(message);
            MessageEntity newMessage = new MessageEntity();
            if (!image.isEmpty()) {
                String fileName = saveFile(image);
                newMessage.setContent("/uploads/" + fileName);
                newMessage.setType("image");
            } else {
                newMessage.setContent(sanitizedMessage);
                newMessage.setType("text");
            }
            messageRepository.save(newMessage);
            ChatWebSocketHandler.broadcastMessage(new TextMessage(sanitizedMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/chat";
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        return fileName;
    }
}