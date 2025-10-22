package com.catalogoLojaProdutos.repository;

import com.catalogoLojaProdutos.model.TokenRedefinicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenRedefinicao, Long> {
    Optional<TokenRedefinicao> findByToken(String token);
    void deleteByToken(String token);
}
