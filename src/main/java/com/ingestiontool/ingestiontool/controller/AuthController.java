package com.ingestiontool.ingestiontool.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ingestiontool.ingestiontool.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/token")
    public String getToken(@RequestParam String username) {
        return JwtUtil.generateToken(username);
    }
}
