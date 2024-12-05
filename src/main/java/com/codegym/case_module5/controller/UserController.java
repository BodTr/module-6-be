package com.codegym.case_module5.controller;

import com.codegym.case_module5.dto.RegisterRequest;
import com.codegym.case_module5.dto.UpdateProfileRequest;
import com.codegym.case_module5.model.User;
import com.codegym.case_module5.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (userService.isEmailExist(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã tồn tại. Vui lòng sử dụng email khác.");
        }

        userService.registerUser(registerRequest);
        return ResponseEntity.ok("Đăng ký thành công.");
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(Principal principal) {
        String email = principal.getName(); // Lấy email từ JWT
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileRequest updateProfileRequest) {
        User updatedUser = userService.updateProfile(updateProfileRequest);
        return ResponseEntity.ok(updatedUser);
    }

}

