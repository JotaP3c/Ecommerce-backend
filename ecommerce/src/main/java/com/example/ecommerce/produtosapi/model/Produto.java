package com.example.ecommerce.produtosapi.model;

import jakarta.persistence.*;
import  lombok.*;

@Entity
@Table(name = "produtos")
@Data

public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column (nullable = false) /* Campo obrigatório */
    private String nome;

    @Column(length = 500)
    private String descricao; /* Limita a 500 registros a descrição */

    @Column (nullable = false)
    private Double preco;

    @Column (nullable = false)
    private boolean ativo;

    public String getNome(){
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public Double getPreco(){
        return preco;
    }

    public void setPreco(Double preco){
        this.preco = preco;
    }

    public boolean isAtivo (){
        return ativo;
    }

    public void setAtivo(Boolean ativo){
        this.ativo = ativo;
    }
}
