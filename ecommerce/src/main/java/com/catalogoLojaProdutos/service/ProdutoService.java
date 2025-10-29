package com.catalogoLojaProdutos.service;

import com.catalogoLojaProdutos.model.Produto;
import com.catalogoLojaProdutos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    public List<Produto> listarTodosSemPaginacao() {
        return produtoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto) {
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

    public List<Produto> listarPaginado(int page, int size, String sortBy) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return produtoRepository.findByAtivoTrue(pageable).getContent();
    }

    public Produto desativarProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setAtivo(false);
        return produtoRepository.save(produto);
    }

    public List<Produto> listarPaginado(Pageable pageable) {
        Page<Produto> pagina = produtoRepository.findAll(pageable);
        return pagina.getContent();
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
