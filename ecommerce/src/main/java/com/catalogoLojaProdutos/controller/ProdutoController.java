package com.catalogoLojaProdutos.controller;

import com.catalogoLojaProdutos.model.Produto;
import com.catalogoLojaProdutos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "200") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        List<Produto> produtos = produtoService.listarPaginado(page, size, sortBy);
        return ResponseEntity.ok(produtos);
    }
    @GetMapping("/todos")
    public List<Produto> listarTodos() {
        return produtoService.listarTodosSemPaginacao();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid  @RequestBody Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            String mensagemErro = result.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(mensagemErro);
        }

        Produto salvo = produtoService.salvar(produto);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        try {
            Produto atualizado = produtoService.atualizar(id, produto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Produto> desativarProduto(@PathVariable Long id) {
        Produto desativado = produtoService.desativarProduto(id);
        return ResponseEntity.ok(desativado);
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<Produto> ativarProduto(@PathVariable Long id) {
        Produto ativado = produtoService.ativarProduto(id);
        return ResponseEntity.ok(ativado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ativos")
    public List<Produto> listarAtivos() {
        return produtoService.listarAtivos();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImagem(@RequestParam("file") MultipartFile file) {
        try {
            String nomeArquivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path caminho = Paths.get("uploads/" + nomeArquivo);
            Files.createDirectories(caminho.getParent());
            Files.write(caminho, file.getBytes());
            return ResponseEntity.ok("{\"url\":\"/uploads/" + nomeArquivo + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao salvar imagem.");
        }
    }
}
