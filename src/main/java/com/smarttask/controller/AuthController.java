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
@CrossOrigin(origins = "https://smart-front-iota.vercel.app", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // Authenticate email/password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Incorrect email or password", e);
        }

        // Load the full User object (not just UserDetails)
        var user = userService.getUserByEmail(authRequest.getEmail());
        String jwt = jwtUtil.generateToken(user.getEmail(), user.getRole().name()); // include role in token

        return new AuthResponse(jwt, user.getRole().name()); // include jwt + role
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