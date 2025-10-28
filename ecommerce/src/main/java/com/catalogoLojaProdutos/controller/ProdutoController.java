package com.catalogoLojaProdutos.controller;

import com.catalogoLojaProdutos.model.Produto;
import com.catalogoLojaProdutos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController

@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> listar( //achp q nao ta em uso, entender isso
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "200") int size, // limita os registros buscados por paginação, ver se funciona
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        List<Produto> produtos = produtoService.listarPaginado(page, size, sortBy);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Produto criar(@RequestBody Produto produto) {
        return produtoService.salvar(produto);
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
    public ResponseEntity<Produto> desativarProduto(@PathVariable Long id)
    {
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


}
