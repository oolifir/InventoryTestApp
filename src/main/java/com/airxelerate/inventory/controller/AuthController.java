package com.airxelerate.inventory.controller;

import com.airxelerate.inventory.dto.UserDTO;
import com.airxelerate.inventory.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class AuthController {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(JWTUtil jwtUtil, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/auth")
    @ResponseBody
    public Map<String, String> performLogin(@RequestBody UserDTO userDTO) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());

        if (passwordEncoder.matches(userDTO.getPassword(), userDetails.getPassword())) {
            String token = jwtUtil.generateToken(userDTO.getUsername());
            return Map.of("jwt-token", token);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }

}