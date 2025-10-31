package com.catalogoLojaProdutos.controller;

import com.catalogoLojaProdutos.Dto.RedefinirSenhaDTO;
import com.catalogoLojaProdutos.service.usuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private usuarioService usuarioService;

    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        usuarioService.enviarTokenRedefinicao(email);
        return ResponseEntity.ok("Enviamos um e-mail com instruções de redefinição.");
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody RedefinirSenhaDTO dto) {
        usuarioService.redefinirSenha(dto);
        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }
}
