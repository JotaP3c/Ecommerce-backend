package com.catalogoLojaProdutos.repository;

import com.catalogoLojaProdutos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByAtivoTrue();
    Optional<Produto> findByNomeAndDescricao(String nome, String descricao);
    Optional<Produto> findByNomeIgnoreCaseAndDescricaoIgnoreCase(String nome, String descricao);

}
