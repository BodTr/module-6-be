package com.codegym.case_module5.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String displayName;

    private String phone;

    @Column(nullable = false)
    private String password;

    private LocalDate dateOfBirth;

    @Column(length = 6)
    private String gender;

    @Lob // Để lưu ảnh avatar dưới dạng byte[]
    private byte[] avatar;

    private String avatarPath; // Đường dẫn ảnh đại diện

    // Getters và Setters
    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }




    }




