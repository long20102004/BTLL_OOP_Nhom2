package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping("/")
    public ModelAndView homePage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Profile");
        return modelAndView;
    }
}
