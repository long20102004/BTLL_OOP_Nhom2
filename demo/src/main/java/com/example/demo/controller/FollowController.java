package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
    @Autowired
    private UserService followService;

    @PostMapping("/{followerId}/{followedId}")
    public void followUser(@PathVariable int followerId, @PathVariable int followedId) {
        followService.followUser(followerId, followedId);
    }

    @DeleteMapping("/{followerId}/{followedId}")
    public void unfollowUser(@PathVariable int followerId, @PathVariable int followedId) {
        followService.unfollowUser(followerId, followedId);
    }
}