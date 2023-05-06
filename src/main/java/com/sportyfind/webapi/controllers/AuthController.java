package com.sportyfind.webapi.controllers;

import com.sportyfind.webapi.dtos.*;
import com.sportyfind.webapi.models.AppUserEntity;
import com.sportyfind.webapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    @Autowired
    private JwtEncoder jwtEncoder;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(AuthLoginReqDto authLoginReqDto) {
        var status = HttpStatus.OK;
        var message = "Login success";
        var result = new SuccessResponseDto();
        try {

        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            message = "Login failed";
        }
        result.message = message;
        return new ResponseEntity<>(result, status);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(AuthCreateReqDto authCreateReqDto) {
        var status = HttpStatus.OK;
        var message = "Register success";
        var result = new SuccessResponseDto();
        try {

        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            message = "Register failed";
        }
        result.message = message;
        return new ResponseEntity<>(result, status);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/authenticate")
    public Authentication authenticate(Authentication authentication) {
        return authentication;
    }
}
