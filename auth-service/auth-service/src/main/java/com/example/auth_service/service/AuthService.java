package com.example.auth_service.service;

import com.example.auth_service.entity.User;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    public JwtUtil jwtUtil;
    @Autowired
    UserRepository repo;

    @Autowired
    PasswordEncoder encoder;

    public void register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(com.example.auth_service.entity.Role.USER);
        repo.save(user);
    }
    public String login(User user) {

        User dbUser = repo.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(
                dbUser.getUsername(),
                dbUser.getRole().name()

        );
    }
}