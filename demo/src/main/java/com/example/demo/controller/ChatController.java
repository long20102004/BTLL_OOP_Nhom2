package com.example.demo.controller;

//import com.example.demo.sse.SSEHandler;
import com.example.demo.model.MessageEntity;
import com.example.demo.model.User;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.socket.ChatWebSocketHandler;
import com.example.demo.util.InputSanitizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private SSEHandler sseHandler;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";


    @GetMapping("/chat")
    public String getChatPage(Model model) {
        addModelAttributes(model);
        return "chat";
    }

    @GetMapping("/chat/{receiverId}")
    public ModelAndView chat(@PathVariable int receiverId, Model model) {
        addModelAttributes(model);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User receiver = userRepository.findById(receiverId);
        List<MessageEntity> messageEntities = messageRepository.findAllBySenderIdAndReceiverId(currentUser.getId(), receiverId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("messages", messageEntities);
        modelAndView.setViewName("chat");
        modelAndView.addObject("receiver", receiver);
        return modelAndView;
    }

    private void addModelAttributes(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> users = userRepository.findAll();
        List<User> otherUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getId() != currentUser.getId()) {
                otherUsers.add(user);
            }
        }
        model.addAttribute("otherUsers", otherUsers);
        model.addAttribute("currentUser", currentUser);
    }

//    @PostMapping("/chat/{receiverId}")
//    public String postMessage(@RequestParam("message") String message,
//                              @RequestParam("image") MultipartFile image,
//                              @PathVariable int receiverId,
//                              RedirectAttributes redirectAttributes) {
//        try {
//            String sanitizedMessage = InputSanitizer.sanitize(message);
//            MessageEntity newMessage = new MessageEntity();
//            if (!image.isEmpty()) {
//                String fileName = saveFile(image);
//                newMessage.setImgUrl("/uploads/" + fileName);
//            }
//            newMessage.setContent(sanitizedMessage);
//            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            User receiver = userRepository.findById(receiverId);
//            newMessage.setSender(currentUser);
//            newMessage.setReceiver(receiver);
//            newMessage.setTimeSend(new Timestamp(System.currentTimeMillis()));
//            messageRepository.save(newMessage);
////            sseHandler.sendMessage(sanitizedMessage);
//            ChatWebSocketHandler.broadcastMessage(new TextMessage(sanitizedMessage));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        redirectAttributes.addFlashAttribute("message", message);
//        return "redirect:/chat/{receiverId}";
//    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        return fileName;
    }
}