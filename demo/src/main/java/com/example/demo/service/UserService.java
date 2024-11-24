package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.UserFollow;
import com.example.demo.repository.UserFollowRepository;
import com.example.demo.repository.UserRepository;
//import com.example.demo.security.JwtUtility;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private UserFollowRepository userFollowRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public void saveUser(User user){
        userRepository.save(user);
    }
    public void followUser(int followerId, int followingId) {
        User follower = userRepository.findById(followerId);
        User following = userRepository.findById(followingId);

        if (userFollowRepository.findByFollowerAndFollowing(follower, following).isEmpty()) {
            UserFollow userFollow = new UserFollow();
            userFollow.setFollower(follower);
            userFollow.setFollowing(following);
            userFollow.setCreatedAt(new Date(System.currentTimeMillis()));

            userFollowRepository.save(userFollow);
        }
    }
    @Transactional
    public void unfollowUser(int followerId, int followingId) {
        User follower = userRepository.findById(followerId);
        User following = userRepository.findById(followingId);

        userFollowRepository.deleteByFollowerAndFollowing(follower, following);
    }

    public boolean isFollowing(int followerId, int followingId) {
        User follower = userRepository.findById(followerId);
        User following = userRepository.findById(followingId);

        if (follower == null || following == null) {
            return false;
        }

        return userFollowRepository.findByFollowerAndFollowing(follower, following).isPresent();
    }
}
