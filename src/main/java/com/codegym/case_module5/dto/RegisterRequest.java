package com.codegym.case_module5.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String displayName;
    private String phone;
    private String password;
}

