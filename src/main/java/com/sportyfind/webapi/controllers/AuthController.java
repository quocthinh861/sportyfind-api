package com.sportyfind.webapi.controllers;

import com.sportyfind.webapi.dtos.*;
import com.sportyfind.webapi.jwt.JwtTokenProvider;
import com.sportyfind.webapi.entities.UserEntity;
import com.sportyfind.webapi.repositories.UserRepository;
import com.sportyfind.webapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    public AuthController(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder,
            UserService userService
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthLoginReqDto authLoginReqDto) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            // Xác thực nguời dùng.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authLoginReqDto.username, authLoginReqDto.password)
            );

            // Tạo jwt.
            String token = jwtTokenProvider.generateToken((UserDetails) authentication.getPrincipal());

            // Trả về jwt cho người dùng.
            response.result = AuthLoginResDto.builder()
                    .token(token)
                    .user(authentication.getPrincipal())
                    .build();
            return new ResponseEntity<>(response, status);
        } catch (Exception err) {
            var response = new ErrorResponseDto();
            response.errors = err;
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody AuthCreateReqDto authCreateReqDto) {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();

            // Kiêm tra username đã tồn tại chưa.
            if (userRepository.findByUsername(authCreateReqDto.username) != null) {
                return new ResponseEntity<>(new ErrorResponseDto("Tên đăng nhập đã tồn tại!"), HttpStatus.BAD_REQUEST);
            }

            // Lưu thông tin đăng ký vào database.
            userService.createUser(authCreateReqDto);

            response.message = "Đăng ký thành công!";
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = e;
            return new ResponseEntity<>(response, status);
        }
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> test() {
        var status = HttpStatus.OK;
        try {
            var response = new SuccessResponseDto();
            response.message = "Test thành công!";
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            var response = new ErrorResponseDto();
            response.errors = e;
            return new ResponseEntity<>(response, status);
        }
    }
}
