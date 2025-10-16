package com.catalogoLojaProdutos.controller;

import com.catalogoLojaProdutos.model.Pessoa;
import com.catalogoLojaProdutos.repository.PessoaRepository;
import com.catalogoLojaProdutos.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final PessoaRepository pessoaRepository;

    public AuthController(JwtService jwtService, PessoaRepository pessoaRepository) {
        this.jwtService = jwtService;
        this.pessoaRepository = pessoaRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user) {
        String email = user.get("username");
        String senha = user.get("password");

        Optional<Pessoa> pessoaOpt = pessoaRepository.findByEmail(email);

        if (pessoaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Usuário não encontrado"));
        }

        Pessoa pessoa = pessoaOpt.get();

        if (!pessoa.getSenha().equals(senha)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Senha incorreta"));
        }

        String token = jwtService.generateToken(email);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "nome", pessoa.getNome(),
                "email", pessoa.getEmail(),
                "perfil", pessoa.isPerfil() // true = admin, false = cliente
        ));
    }
}
