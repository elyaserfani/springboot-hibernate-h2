package com.simplespringboot.app.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class MainController {
    @GetMapping("/")
    public String homePage(Model model) {
        return "index";
    }
}
