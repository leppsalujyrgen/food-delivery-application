package com.example.fooddelivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/api")
    public String getApiHomePage() {
        return "api_overview";
    }
}