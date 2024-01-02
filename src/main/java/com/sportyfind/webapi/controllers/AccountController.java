package com.sportyfind.webapi.controllers;

import com.sportyfind.webapi.dtos.ErrorResponseDto;
import com.sportyfind.webapi.dtos.FieldBookingDto;
import com.sportyfind.webapi.dtos.SuccessResponseDto;
import com.sportyfind.webapi.dtos.UserCreateResDto;
import com.sportyfind.webapi.entities.UserEntity;
import com.sportyfind.webapi.services.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    UserService userService;

    @GetMapping("/getUserInfo")
    public ResponseEntity<Object> getUserInfoById(@RequestParam Long userId) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = userService.getUserById(userId);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @PostMapping("/getUserInfoByUserIds")
    public ResponseEntity<Object> getUserInfoByIds(@RequestBody Long[] userIds) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = userService.getUserByIds(userIds);
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @PostMapping("/updateUserInfo")
    public ResponseEntity<Object> updateUser(@RequestBody UserCreateResDto userCreateResDto) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = userService.updateUser(userCreateResDto);
            if (response.result == null) {
                status = HttpStatus.BAD_REQUEST;
            }
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }

    @PostMapping("/updateThumbnail")
    public ResponseEntity<Object> updateThumbnail(@RequestBody UserCreateResDto userCreateResDto) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.result = userService.updateThumbnail(userCreateResDto);
            if (response.result == null) {
                status = HttpStatus.BAD_REQUEST;
            }
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, status);
        }
    }
}
