package com.example.demo.controller;

import com.example.demo.model.PostEntity;
import com.example.demo.model.TagEntity;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private TagRepository tagRepository;
    private UserRepository userRepository;
    private Utility utility;
    private PostRepository postRepository;
    private UserService userService;

    @PostMapping("/user/setup")
    @ResponseBody
    public String setUpHabit(@RequestBody List<String> userHabits) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TagEntity> tagEntities = tagRepository.findAll();
        List<TagEntity> userTags = new ArrayList<>();
        for (TagEntity tagEntity : tagEntities) {
            for (String tag : userHabits) {
                if (tagEntity.getName().equals(tag)) {
                    userTags.add(tagEntity);
                }
            }
        }
        user.setTagEntities(userTags);
        userRepository.save(user);
        return "success";
    }


    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam("newDisplayName") String newDisplayName,
                              @RequestParam("avatar") MultipartFile avatar) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setDisplayName(newDisplayName);
        if (!avatar.isEmpty()) {
           String avatarUrl = utility.saveFile(avatar);
           user.setAvatar(avatarUrl);
        }
        userRepository.save(user);
        return "redirect:/profile";
    }
    @GetMapping("/profile/{profileId}")
    public ModelAndView showProfile(@PathVariable int profileId){
        ModelAndView modelAndView = new ModelAndView();
        User user = userRepository.findById(profileId);
        List<PostEntity> postEntityList = new ArrayList<>();
        List<PostEntity> allPost = postRepository.findAll();
        for (PostEntity postEntity : allPost) {
            if (postEntity.getUserPost().getId() == user.getId()) {
                postEntityList.add(postEntity);
            }
        }
        modelAndView.addObject("posts", postEntityList);
        modelAndView.addObject("user", user);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUserName);
        modelAndView.addObject("currentUserId", currentUser.getId());
        boolean isFollowing = userService.isFollowing(currentUser.getId(), user.getId());
        modelAndView.addObject("isFollowing", isFollowing);
        modelAndView.setViewName("profile");
        return modelAndView;
    }
}