package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class SubscriberController {

    @GetMapping("/status")
    public String healthChecker() {
        return "ok";
    }
}
