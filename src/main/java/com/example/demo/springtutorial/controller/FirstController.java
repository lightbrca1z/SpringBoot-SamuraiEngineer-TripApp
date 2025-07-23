package com.example.demo.springtutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/") // ルートパス
    public String root() {
        return "redirect:/first"; // /firstにリダイレクト
    }

    @GetMapping({"/first", "/first/"}) // 末尾スラッシュありなし両方に対応
    public String showFirstView() {
        return "firstView"; // ← templates/firstView.html を表示
    }

}