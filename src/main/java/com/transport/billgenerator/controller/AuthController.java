package com.transport.billgenerator.controller;

import com.transport.billgenerator.dto.AuthRequest;
import com.transport.billgenerator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // Default dev bypass or DB validation
        if ("admin".equals(request.getUsername()) && "admin123".equals(request.getPassword()) ||
                userService.authenticate(request.getUsername(), request.getPassword())) {

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login Successful");
            response.put("redirect", "/dashboard");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body("Invalid Username or Password");
    }
}