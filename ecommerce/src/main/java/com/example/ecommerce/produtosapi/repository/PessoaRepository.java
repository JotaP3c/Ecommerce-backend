package com.example.ecommerce.produtosapi.repository;


import com.example.ecommerce.produtosapi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository  extends JpaRepository<Pessoa, Long> {
}
