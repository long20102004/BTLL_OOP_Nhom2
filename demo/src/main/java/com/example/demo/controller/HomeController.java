package com.example.demo.controller;

import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.UserRegisterDto;
import com.example.demo.model.PostEntity;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView();
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            modelAndView.addObject("userLoginDto", new UserLoginDto());
            modelAndView.setViewName("login");
            System.out.println("not logged");
        } else {
            List<PostEntity> postEntityList = postRepository.findAll();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            modelAndView.addObject("posts", postEntityList);
            User user = userRepository.findByUsername(authentication.getName());
            modelAndView.addObject("user", user);
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profilePage() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @GetMapping("/sign-up")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        modelAndView.addObject("user", new UserRegisterDto());
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @PostMapping("/sign-up")
    public String registerUser(@ModelAttribute("user") @Valid UserRegisterDto userRegisterDto, BindingResult result) {
        if (result.hasErrors()) {
            return "signup";
        }
        User user = new User(userRegisterDto);
        userRepository.save(user);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid UserLoginDto userLoginDto, HttpServletRequest request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/login?error";
        }

        User user = userRepository.findByUsername(userLoginDto.getUsername());
        if (user != null && userLoginDto.getPassword().equals(user.getPassword())) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());

            return "redirect:/";
        } else {
            model.addAttribute("loginError", "Invalid username or password");
            return "login";
        }
    }


}
