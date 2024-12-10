package com.codegym.case_module5.controller;

import com.codegym.case_module5.dto.RegisterRequest;
import com.codegym.case_module5.dto.UpdateProfileRequest;
import com.codegym.case_module5.model.User;
import com.codegym.case_module5.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;

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
        if (user.getDateOfBirth() == null) {
            user.setDateOfBirth(LocalDate.now()); // Gán giá trị mặc định nếu null
        }

        // Kiểm tra nếu user không có avatar, gán avatar mặc định
        if (user.getAvatarPath() != null && !user.getAvatarPath().isEmpty()) {
            user.setAvatarPath("/api/uploads/" + Paths.get(user.getAvatarPath()).getFileName().toString());
        } else {
            user.setAvatarPath("/uploads/default-avatar.png"); // Avatar mặc định
        }
        return ResponseEntity.ok(user);
    }


    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(
            @ModelAttribute @Valid UpdateProfileRequest updateRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // Xử lý file avatar
        String avatarUrl = null;
        if (updateRequest.getAvatar() != null && !updateRequest.getAvatar().isEmpty()) {
            try {
                // Lưu file vào thư mục "uploads/" trong thư mục root của dự án
                String fileName = System.currentTimeMillis() + "_" + updateRequest.getAvatar().getOriginalFilename();
                Path filePath = Paths.get("uploads/" + fileName);
                Files.createDirectories(filePath.getParent()); // Tạo thư mục nếu chưa có
                Files.copy(updateRequest.getAvatar().getInputStream(), filePath);
                avatarUrl = "/uploads/" + fileName;
                System.out.println("Avatar đã được lưu tại: " + avatarUrl);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lưu avatar.");
            }
        } else {
            System.out.println("Không có file avatar được gửi lên.");
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

    // API lấy danh sách người dùng
    @GetMapping("/list")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<User> users = userService.getAllUsers(PageRequest.of(page, size));
        return ResponseEntity.ok(users);
    }


}





