package com.codegym.case_module5.controller;

import com.codegym.case_module5.dto.RegisterRequest;
import com.codegym.case_module5.dto.UpdateProfileRequest;
import com.codegym.case_module5.model.User;
import com.codegym.case_module5.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@Validated
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
    public ResponseEntity<?> getProfile(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không thể xác thực người dùng.");
        }
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.ok(user);
    }


    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(
            @ModelAttribute @Valid UpdateProfileRequest updateRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        if (updateRequest.getAvatar() != null && !updateRequest.getAvatar().isEmpty()) {
            System.out.println("Avatar file: " + updateRequest.getAvatar().getOriginalFilename());
        } else {
            System.out.println("Không có file avatar được gửi lên");
        }

        // Xử lý các trường khác
        System.out.println("Tên hiển thị: " + updateRequest.getDisplayName());
        System.out.println("Ngày sinh: " + updateRequest.getDateOfBirth());
        System.out.println("Giới tính: " + updateRequest.getGender());
        System.out.println("Email: " + updateRequest.getEmail());
        try {
            userService.updateProfile(updateRequest);
            return ResponseEntity.ok("Cập nhật thông tin thành công.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật thông tin.");
        }
    }
}





