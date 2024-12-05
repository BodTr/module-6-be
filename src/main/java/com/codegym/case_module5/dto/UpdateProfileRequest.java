package com.codegym.case_module5.dto;


import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UpdateProfileRequest {

    @NotBlank(message = "Tên hiển thị không được để trống.")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Tên hiển thị không được chứa ký tự đặc biệt.")
    private String displayName;

    private LocalDate dateOfBirth;

    @Pattern(regexp = "Male|Female|Other", message = "Giới tính chỉ được là Male, Female hoặc Other.")
    private String gender;

    @NotBlank(message = "Email không được để trống.")
    @Email(message = "Email không hợp lệ.")
    private String email;

    @Size(max = 15, message = "Số điện thoại không được vượt quá 15 ký tự.")
    private String phone;

    // Getters và Setters
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

