package com.codegym.music_project.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.management.relation.Role;
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

    @Column(nullable = false)
    private String role;

    private String phone;

    @Column(nullable = false)
    private String password;

    private LocalDate dateOfBirth;

    @Column(length = 6)
    private String gender;

    private String avatarPath; // Đường dẫn ảnh đại diện
}
