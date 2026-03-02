package com.codetrace.notification.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private final PasswordEncoder passwordEncoder;
    private final String salt;

    public EncryptionService(@Value("${app.security.salt}") String salt) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.salt = salt;
    }

    public String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword + salt);
    }

    public boolean matches(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword + salt, encryptedPassword);
    }
}

