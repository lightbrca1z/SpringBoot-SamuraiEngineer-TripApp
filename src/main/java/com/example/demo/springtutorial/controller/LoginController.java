package com.example.demo.springtutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping({"/login", "/login/"}) // 末尾スラッシュありなし両方に対応
    public String login() {
        return "login";
    }
}