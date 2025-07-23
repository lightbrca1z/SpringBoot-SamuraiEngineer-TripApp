package com.example.demo.springtutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewDemoController {

    @GetMapping({"/view", "/view/"}) // 末尾スラッシュありなし両方に対応
    public String viewDemo() {
        return "tutorialView"; // ← templates/tutorialView.html を表示
    }

}