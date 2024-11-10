package com.example.demo.controller;

import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;
    @GetMapping("/posts")
    public ModelAndView getAllPosts(){
        List<PostEntity> postEntities = postRepository.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("posts", postEntities);
        modelAndView.setViewName("post");
        return modelAndView;
    }
}
