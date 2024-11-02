package com.example.demo.service;

import com.example.demo.exception_handle.UserExistedException;
import com.example.demo.model.JwtResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtility;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    private AuthenticationManager authenticationManager;
    private JwtUtility jwtUtility;
    private UserRepository userRepository;
    private OAuth2AuthorizedClientService authorizedClientService;

    public AuthService(AuthenticationManager authenticationManager, JwtUtility jwtUtility, UserRepository userRepository, OAuth2AuthorizedClientService authorizedClientService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
        this.userRepository = userRepository;
        this.authorizedClientService = authorizedClientService;
    }


    public JwtResponse signUp(JwtResponse signUpRequest) throws AuthenticationException {
        JwtResponse jwtResponse = new JwtResponse();
        UserDetails userDetails = userRepository.findByUsername(signUpRequest.getUsername());
        if (userDetails != null) throw new UserExistedException("User already existed");
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        user.setRole(signUpRequest.getRole());
        User done = userRepository.save(user);
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setPassword(user.getPassword());
        jwtResponse.setMessage("done");
        return jwtResponse;
    }

    public JwtResponse signIn(JwtResponse signInRequest) throws AuthenticationException {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        User user = userRepository.findByUsername(signInRequest.getUsername());
        System.out.println("User is: " + user.getUsername());
        String jwt = jwtUtility.generateToken(user);
        String refreshToken = jwtUtility.generateRefreshToken(new HashMap<>(), user);
        jwtResponse.setToken(jwt);
        jwtResponse.setRefreshToken(refreshToken);
        return jwtResponse;
    }
}
