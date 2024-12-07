package com.codegym.case_module5.service;

import com.codegym.case_module5.dto.RegisterRequest;
import com.codegym.case_module5.dto.UpdateProfileRequest;
import com.codegym.case_module5.model.User;
import com.codegym.case_module5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setDisplayName(registerRequest.getDisplayName());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User updateProfile(UpdateProfileRequest updateProfileRequest) {
        // Lấy email từ Security Context
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Kiểm tra người dùng có tồn tại không
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("Người dùng không tồn tại.");
        }

        User user = optionalUser.get();

        // Cập nhật thông tin cơ bản
        if (updateProfileRequest.getDisplayName() != null && !updateProfileRequest.getDisplayName().isEmpty()) {
            user.setDisplayName(updateProfileRequest.getDisplayName());
        }
        user.setPhone(updateProfileRequest.getPhone());
        user.setDateOfBirth(updateProfileRequest.getDateOfBirth());
        user.setGender(updateProfileRequest.getGender());

        // Xử lý file upload (nếu có)
        MultipartFile avatar = updateProfileRequest.getAvatar();
        if (avatar != null && !avatar.isEmpty()) {
            try {
                String filePath = "/uploads/" + avatar.getOriginalFilename();
                avatar.transferTo(new java.io.File(filePath));
                user.setAvatarPath(filePath); // Lưu đường dẫn ảnh vào cơ sở dữ liệu
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi lưu ảnh đại diện: " + e.getMessage());
            }
        }

        // Lưu vào cơ sở dữ liệu
        return userRepository.save(user);
    }


}

