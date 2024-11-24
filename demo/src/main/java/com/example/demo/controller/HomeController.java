package com.example.demo.controller;

import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.UserRegisterDto;
import com.example.demo.model.PostEntity;
import com.example.demo.model.TagEntity;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.Utility;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private TagRepository tagRepository;
    private Utility utility;

    @GetMapping("/")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            modelAndView.addObject("userLoginDto", new UserLoginDto());
            modelAndView.setViewName("login");
            System.out.println("not logged");
        } else {
            User user = userRepository.findByUsername(authentication.getName());
            if (user.getTagEntities().isEmpty()) {
                modelAndView.addObject("tags", tagRepository.findAll());
                modelAndView.setViewName("tag");
                modelAndView.addObject("tagChose", new ArrayList<>());
                return modelAndView;
            }
            List<PostEntity> postEntityList = findRelatedPosts(user);
            modelAndView.addObject("posts", postEntityList);
            modelAndView.addObject("user", user);
            modelAndView.setViewName("index");
        }
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
    public String registerUser(@Valid @ModelAttribute("user") UserRegisterDto userRegisterDto,
                               BindingResult result, @RequestParam("avatar") MultipartFile avatar) throws IOException {
        if (result.hasErrors()) {
            return "signup";
        }
        User user = new User(userRegisterDto);
        if (avatar != null && !avatar.isEmpty()) {
            String avatarUrl = utility.saveFile(avatar);
            user.setAvatar(avatarUrl);
        }

        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/tags")
    public String chooseTags(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        List<TagEntity> tagEntities = tagRepository.findAll();
        model.addAttribute("tags", tagEntities);
        List<TagEntity> tagEntities1 = new ArrayList<>(user.getTagEntities());
        for (TagEntity tagEntity : tagEntities1) {
            System.out.println(tagEntity.getName());
        }
        model.addAttribute("tagChose", tagEntities1);
        return "tag";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid UserLoginDto userLoginDto,
                        HttpServletRequest request, BindingResult result, Model model) {
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

    public List<PostEntity> findRelatedPosts(User user) {
        List<PostEntity> relatedPosts = new ArrayList<>();
        List<PostEntity> postEntityList = postRepository.findAll();
        for (PostEntity postEntity : postEntityList) {
            for (TagEntity tagEntity : postEntity.getPostsTag()) {
                if (user.getTagEntities().contains(tagEntity)) {
                    relatedPosts.add(postEntity);
                }
            }
        }
        relatedPosts.sort(new Comparator<PostEntity>() {
            @Override
            public int compare(PostEntity o1, PostEntity o2) {
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        });
        return relatedPosts;
    }

}
