package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.PostService;
import com.example.demo.util.Utility;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
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
    private TagRepository tagRepository;
    private PostService postService;
    private Utility utility;

    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable Integer postId) {
        PostEntity postEntity = postRepository.getReferenceById(postId);
        postRepository.delete(postEntity);
        return "redirect:/";
    }

    @GetMapping("/update/{postId}")
    public ModelAndView updatePost(@PathVariable Integer postId) {
        ModelAndView modelAndView = new ModelAndView();
        PostEntity postEntity = postRepository.getReferenceById(postId);
        modelAndView.addObject("post", postEntity);
        System.out.println(postEntity.getId());
        modelAndView.setViewName("/up");
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUserName);
        modelAndView.addObject("user", user);
        List<TagEntity> tagEntities = tagRepository.findAll();
        modelAndView.addObject("tags", tagEntities);
        return modelAndView;
    }


    @PostMapping("/upvote/{postId}")
    public String upvotePost(@PathVariable int postId){
        postService.handleUpvote(postId);
        return "redirect:/questions/" + postId;
    }

    @PostMapping("/downvote/{postId}")
    public String downVote(@PathVariable int postId){
        postService.handleDownVote(postId);
        return "redirect:/questions/" + postId;
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
        for (CommentEntity comment : commentEntities) {
            for (ReplyEntity replyEntity : replyEntities) {
                if (replyEntity.getCommentReply().getId() == comment.getId()) {
                    replyAdd.add(replyEntity);
                }
            }
        }
        modelAndView.addObject("tags", postEntity.getPostsTag());
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
        List<TagEntity> tagEntities = tagRepository.findAll();
        modelAndView.addObject("tags", tagEntities);
        modelAndView.addObject("post", postEntity);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/up-post")
    public String upPost(PostEntity postEntity, @RequestParam("image")MultipartFile image
    , @RequestParam("tags") List<String> tags) throws IOException {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUserName);
        postEntity.setUserPost(user);
        postEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        if (image != null && !image.isEmpty()) {
            System.out.println("Image received: " + image.getOriginalFilename());
            String imageUrl = utility.saveFile(image);
            postEntity.setImageUrl(imageUrl);
            System.out.println("Image URL: " + imageUrl);
        } else {
            System.out.println("No image received or image is empty");
        }
        List<TagEntity> tagEntities = tagRepository.findAll();
        List<TagEntity> currentTags = new ArrayList<>();
        for (TagEntity tagEntity : tagEntities){
            for (String tag : tags){
                if (tagEntity.getName().equals(tag)){
                    currentTags.add(tagEntity);
                }
            }
        }
        for (String tag : tags){
            System.out.println(tag);
        }
        postEntity.setPostsTag(currentTags);

        postRepository.save(postEntity);
        return "redirect:/";
    }

    @PostMapping("/post/{postId}/comment")
    public String comment(@PathVariable Integer postId, CommentDto commentDto) {
        CommentEntity comment = new CommentEntity();
        comment.setContent(commentDto.getContent());
        PostEntity postEntity = postRepository.getReferenceById(postId);
        comment.setPostComment(postEntity);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUserName);
        comment.setUserComment(user);
        commentRepository.save(comment);
        return "redirect:/questions/" + postId;
    }


}

