package com.example.demo.springtutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/first") // ← ここが無いと /first が処理されない
    public String showFirstView() {
        return "firstView"; // ← templates/firstView.html を表示
    }

}