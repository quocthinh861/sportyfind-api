package com.sportyfind.webapi.controllers;

import com.sportyfind.webapi.dtos.*;
import com.sportyfind.webapi.models.UserEntity;
import com.sportyfind.webapi.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(AuthLoginReqDto authLoginReqDto) {
        var status = HttpStatus.OK;
        var message = "Login success";
        var result = new SuccessResponseDto();
        try {
            UserEntity user = userRepository.findByUsernameAndPassword(authLoginReqDto.username, authLoginReqDto.password);
            if (user == null) {
                status = HttpStatus.NOT_FOUND;
                message = "Username or password is incorrect";
            } else {
                AuthLoginResDto authLoginResDto = new AuthLoginResDto();
                authLoginResDto.username = user.getUsername();
                authLoginResDto.role = user.getUserType();
                authLoginResDto.token = "token";
                result.result = new SingleSuccessResponseResultDto(user);
            }
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
            var user = new UserEntity();
            result.result = new SingleSuccessResponseResultDto(user);
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            message = "Register failed";
        }
        result.message = message;
        return new ResponseEntity<>(result, status);
    }
}
