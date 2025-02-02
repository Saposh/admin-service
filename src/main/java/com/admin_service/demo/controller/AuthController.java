package com.admin_service.demo.controller;

import com.admin_service.demo.Service.UserService;
import com.admin_service.demo.dto.UserDTO;
import com.admin_service.demo.models.User;
import com.admin_service.demo.security.JwtUtil;
import com.admin_service.demo.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil, UserService userService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        User user = userService.registerUser(userDTO.getEmail(), userDTO.getPassword(), userDTO.getRole());
        return ResponseEntity.ok("User registered with email: " + user.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        System.out.println("✅ Успешный вход! Токен: " + token);
        return ResponseEntity.ok(token);
    }
}
