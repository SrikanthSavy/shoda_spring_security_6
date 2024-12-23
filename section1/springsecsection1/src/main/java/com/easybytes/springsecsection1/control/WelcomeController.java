package com.easybytes.springsecsection1.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/welcome")
    public String sayMessage()
    {
        return "Welcome to Spring Web Application WITH SECURITY!";
    }
}
