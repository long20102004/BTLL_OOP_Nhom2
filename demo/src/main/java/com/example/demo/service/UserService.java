package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtility;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    @Autowired
    private JwtUtility jwtUtility;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public User getCurrentUser(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        User user;
        try {
            String userName = jwtUtility.extractUserName(token);
            user = userRepository.findByUsername(userName);
            jwtUtility.isTokenExpired(token);
        } catch (ExpiredJwtException | SignatureException e) {
            return null;
        }
        return user;
    }
}
