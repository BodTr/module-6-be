package com.codegym.music_project.controller;

import com.codegym.music_project.dto.LoginRequest;
import com.codegym.music_project.dto.LoginResponse;
import com.codegym.music_project.dto.RegisterRequest;
import com.codegym.music_project.model.User;
import com.codegym.music_project.service.UserService;
import com.codegym.music_project.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (userService.isEmailExist(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã tồn tại. Vui lòng sử dụng email khác.");
        }
        userService.registerUser(registerRequest);
        return ResponseEntity.ok("Đăng ký thành công!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        System.out.println("Login attempt: " + loginRequest.getEmail());
        User user = userService.findByEmail(loginRequest.getEmail());
        if (user == null) {
            System.out.println("User not found for email: " + loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Email không tồn tại.");
        }

//        System.out.println("User found: " + user.getEmail());
//        System.out.println("Password in DB: " + user.getPassword());
//        System.out.println("Password provided: " + loginRequest.getPassword());

        if (!userService.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
            System.out.println("Password mismatch");
            return ResponseEntity.badRequest().body("Sai mật khẩu.");
        }

        String token = jwtUtil.generateToken(user.getEmail());
//        System.out.println("Generated Token: " + token);
        return ResponseEntity.ok(new LoginResponse(token, user.getDisplayName()));
    }
}