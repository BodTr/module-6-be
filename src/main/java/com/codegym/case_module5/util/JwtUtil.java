package com.codegym.case_module5.util;

import com.codegym.case_module5.config.JwtKeyProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final JwtKeyProvider jwtKeyProvider;

    public JwtUtil(JwtKeyProvider jwtKeyProvider) {
        this.jwtKeyProvider = jwtKeyProvider;
    }

    List<String> defaultRoles = List.of("ROLE_USER");

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", defaultRoles) // Thêm roles vào JWT
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(jwtKeyProvider.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}