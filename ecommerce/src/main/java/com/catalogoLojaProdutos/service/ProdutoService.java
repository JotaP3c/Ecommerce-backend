package com.catalogoLojaProdutos.service;

import com.catalogoLojaProdutos.model.Produto;
import com.catalogoLojaProdutos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto) {
        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        }

        boolean existe = produtoRepository
                .findByNomeIgnoreCaseAndDescricaoIgnoreCase(produto.getNome(), produto.getDescricao())
                .isPresent();

        if (existe) {
            throw new IllegalArgumentException("Já existe um produto com este nome e descrição.");
        }

        return produtoRepository.save(produto);
    }

    public Produto atualizar(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id).map(produto -> {
            produto.setNome(produtoAtualizado.getNome());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setAtivo(produtoAtualizado.isAtivo());
            produto.setId(produtoAtualizado.getId());
            return produtoRepository.save(produto);
        }).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto desativarProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setAtivo(false);
        return produtoRepository.save(produto);
    }

    public Produto ativarProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setAtivo(true);
        return produtoRepository.save(produto);
    }

    public List<Produto> listarAtivos() {
        return produtoRepository.findByAtivoTrue();
    }
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }
}
