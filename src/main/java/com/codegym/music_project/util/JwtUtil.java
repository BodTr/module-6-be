package com.codegym.music_project.util;

import com.codegym.music_project.config.JwtKeyProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final JwtKeyProvider jwtKeyProvider;

    public JwtUtil(JwtKeyProvider jwtKeyProvider) {
        this.jwtKeyProvider = jwtKeyProvider;
    }

    List<String> roles;

    public String generateToken(String email, String role) {
        roles = List.of(role);
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles) // Thêm roles vào JWT
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(jwtKeyProvider.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}