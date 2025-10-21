package com.catalogoLojaProdutos.controller;

import com.catalogoLojaProdutos.model.Pessoa;
import com.catalogoLojaProdutos.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = "http://localhost:4200")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<Pessoa>> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Boolean perfil,
            @RequestParam(required = false) Boolean ativo
    ) {
        if (nome != null && !nome.isEmpty()) {
            return ResponseEntity.ok(pessoaService.buscarPorNome(nome));
        } else if (email != null && !email.isEmpty()) {
            return ResponseEntity.ok(pessoaService.buscarPorEmail(email));
        } else if (id != null) {
            return pessoaService.buscarPorId(id)
                    .map(pessoa -> ResponseEntity.ok(List.of(pessoa)))
                    .orElse(ResponseEntity.notFound().build());
        } else if (perfil != null) {
            return ResponseEntity.ok(pessoaService.buscarPorPerfil(perfil));
        } else if (ativo != null) {
            return ResponseEntity.ok(pessoaService.buscarPorAtivo(ativo));
        } else {
            return ResponseEntity.ok(pessoaService.listarTodos());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
        return pessoaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pessoa criar(@RequestBody Pessoa pessoa) {
        pessoa.setAtivo(true); // garante que novos usuários são ativos
        return pessoaService.salvar(pessoa);
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Pessoa> desativar(@PathVariable Long id) {
        return pessoaService.buscarPorId(id)
                .map(pessoa -> {
                    pessoa.setAtivo(false);
                    Pessoa atualizado = pessoaService.salvar(pessoa);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        try {
            Pessoa atualizado = pessoaService.atualizar(id, pessoa);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
