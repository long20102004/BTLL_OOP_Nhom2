//package com.example.demo.controller;
//
//import com.example.demo.model.InvalidToken;
//import com.example.demo.model.JwtResponse;
//import com.example.demo.model.User;
//import com.example.demo.repository.InvalidTokenRepository;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.security.JwtUtility;
//import com.example.demo.service.AuthService;
//import com.example.demo.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.Set;
//
//@Controller
//public class UserController {
//    private AuthService authService;
//    private JwtUtility jwtUtility;
//    @Autowired
//    private InvalidTokenRepository invalidTokenRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    public UserController(AuthService authService, JwtUtility jwtUtility) {
//        this.authService = authService;
//        this.jwtUtility = jwtUtility;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<JwtResponse> signIn(@RequestBody JwtResponse signInRequest) {
//        return ResponseEntity.ok(authService.signIn(signInRequest));
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<JwtResponse> signUp(@RequestBody JwtResponse signUpRequest) {
//        return ResponseEntity.ok(authService.signUp(signUpRequest));
//    }
//
//    @GetMapping("/valid")
//    public ResponseEntity<String> getProfile(HttpServletRequest request) {
//        User user = userService.getCurrentUser(request);
//        if (user == null) {
//            return new ResponseEntity<>("Token is invalid", HttpStatus.UNAUTHORIZED);
//        }
//        return new ResponseEntity<>("User is valid", HttpStatus.OK);
//    }
//
//    @PostMapping("/logoutt")
//    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
//        InvalidToken invalidToken = new InvalidToken();
//        String token = request.getHeader("Authorization").substring(7);
//        invalidToken.setToken(token);
//        invalidTokenRepository.save(invalidToken);
//        System.out.println("LOG OUT");
//        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
//    }
//
//    @Scheduled(fixedRate = 1000 * 60 * 60) // Run every 60 minutes
//    public void purgeAll() {
//        System.out.println("DELETE ALL INVALID TOKEN");
//        invalidTokenRepository.deleteAll();
//    }
//
//
//}
