package com.codegym.case_module5.service;

import com.codegym.case_module5.dto.RegisterRequest;
import com.codegym.case_module5.dto.UpdateProfileRequest;
import com.codegym.case_module5.model.User;
import com.codegym.case_module5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        // Lấy thông tin người dùng từ email
        Optional<User> optionalUser = userRepository.findByEmail(updateProfileRequest.getEmail());
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("Người dùng không tồn tại.");
        }

        User user = optionalUser.get();

        // Cập nhật thông tin
        user.setDisplayName(updateProfileRequest.getDisplayName());
        user.setPhone(updateProfileRequest.getPhone());
        user.setDateOfBirth(updateProfileRequest.getDateOfBirth());
        user.setGender(updateProfileRequest.getGender());

        // Lưu vào cơ sở dữ liệu
        return userRepository.save(user);
    }
}

