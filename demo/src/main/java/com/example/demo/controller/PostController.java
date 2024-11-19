package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private ReplyRepository replyRepository;
    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable Integer postId) {
        PostEntity postEntity = postRepository.getReferenceById(postId);
        postRepository.delete(postEntity);
        return "redirect:/";
    }
    @GetMapping("/update/{postId}")
    public ModelAndView updatePost(@PathVariable Integer postId){
        ModelAndView modelAndView = new ModelAndView();
        PostEntity postEntity = postRepository.getReferenceById(postId);
        modelAndView.addObject("post", postEntity);
        System.out.println(postEntity.getId());
        modelAndView.setViewName("/up");
        return modelAndView;
    }

    @GetMapping("/questions/{postId}")
    public ModelAndView showPost(@PathVariable Integer postId) {
        PostEntity postEntity = postRepository.getReferenceById(postId);
        ModelAndView modelAndView = new ModelAndView();
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUserName);
        List<CommentEntity> commentEntities = commentRepository.getCommentEntitiesByPostId(postId);
        List<ReplyEntity> replyEntities = replyRepository.findAll();
        List<ReplyEntity> replyAdd = new ArrayList<>();
        for (CommentEntity comment : commentEntities){
            for (ReplyEntity replyEntity : replyEntities){
                if (replyEntity.getCommentReply().getId() == comment.getId()){
                    replyAdd.add(replyEntity);
                }
            }
        }
        modelAndView.addObject("replies", replyAdd);
        modelAndView.addObject("comments", commentEntities);
        modelAndView.addObject("post", postEntity);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("post");
        return modelAndView;
    }

    @GetMapping("/ask-question")
    public ModelAndView askQuestion() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("up");
        PostEntity postEntity = new PostEntity();
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUserName);
        modelAndView.addObject("post", postEntity);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/up-post")
    public String upPost(PostEntity postEntity) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUserName);
        postEntity.setUserPost(user);
        postEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        postRepository.save(postEntity);
        return "redirect:/";
    }

    @PostMapping("/post/{postId}/comment")
    public String comment(@PathVariable Integer postId, CommentDto commentDto){
        CommentEntity comment = new CommentEntity();
        comment.setContent(commentDto.getContent());
        PostEntity postEntity = postRepository.getReferenceById(postId);
        comment.setPostComment(postEntity);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUserName);
        comment.setUserComment(user);
        commentRepository.save(comment);
        return "redirect:/questions/}" + postId;
    }


}

