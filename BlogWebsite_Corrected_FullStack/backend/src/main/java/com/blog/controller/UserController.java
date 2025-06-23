package com.blog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome, authenticated user!";
    }
}
