package com.codegym.case_module5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Cấu hình SecurityFilterChain để bảo mật ứng dụng
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF
                .cors(cors -> cors.disable()) // Vô hiệu hóa CORS (nếu cần)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Cho phép các endpoint không cần xác thực
                        .anyRequest().authenticated() // Các endpoint khác yêu cầu xác thực
                )
                .httpBasic(httpBasic -> httpBasic.disable()); // Vô hiệu hóa HTTP Basic Authentication (nếu không cần)

        return http.build();
    }
}
