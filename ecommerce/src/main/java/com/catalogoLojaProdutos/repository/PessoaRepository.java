package com.catalogoLojaProdutos.repository;


import com.catalogoLojaProdutos.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    List<Pessoa> findByNomeContainingIgnoreCase(String nome);
    List<Pessoa> findByEmailContainingIgnoreCase(String email);
    Optional<Pessoa> findByEmail(String email);
    List<Pessoa> findByPerfil(Boolean perfil);
    List<Pessoa> findByAtivo(Boolean ativo);
    List<Pessoa> findPessoaTeste(Long id);
}