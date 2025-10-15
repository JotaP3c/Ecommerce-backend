package com.example.ecommerce.produtosapi.controller;

import com.example.ecommerce.produtosapi.security.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> user) {
        String username = user.get("username");

        /* aqui  validaria a senha no banco (exemplo simplificado) */
        String token = jwtService.generateToken(username);

        return Map.of("token", token);
    }
}
