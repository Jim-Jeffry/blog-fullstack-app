package com.blog.controller;

import com.blog.dto.AuthRequest;
import com.blog.dto.AuthResponse;
import com.blog.dto.LoginRequest;
import com.blog.entity.User;
import com.blog.repository.UserRepository;
import com.blog.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Register New User
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getEmail().split("@")[0]) // auto username
                .role("AUTHOR") // default role
                .build();

        userRepo.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }

    // Login and Generate Token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("Login attempt for email: " + request.getEmail());

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );
            System.out.println("Authentication successful");
        } catch (BadCredentialsException e) {
            System.out.println("Authentication failed: Bad credentials");
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());
        System.out.println("Generated token: " + token);

        return ResponseEntity.ok(new AuthResponse(token, user.getRole(), user.getEmail()));
    }

    




}
