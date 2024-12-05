package com.codegym.case_module5.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
