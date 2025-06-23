package com.smarttask.controller;

import com.smarttask.dto.AuthRequest;
import com.smarttask.dto.AuthResponse;
import com.smarttask.dto.UserRequestDTO;
import com.smarttask.dto.UserResponseDTO;
import com.smarttask.security.JwtUtil;
import com.smarttask.security.MyUserDetailsService;
import com.smarttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private MyUserDetailsService userDetailsService; // Fixed: Use separate service

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // Use email instead of username since your User entity uses email
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Incorrect email or password", e);
        }
        final var userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        return new AuthResponse(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO userDTO) {
        try {
            UserResponseDTO newUser = userService.createUser(userDTO);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
