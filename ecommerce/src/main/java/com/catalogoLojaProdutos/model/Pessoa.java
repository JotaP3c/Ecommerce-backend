package com.catalogoLojaProdutos.model;

import jakarta.persistence.*;
import  lombok.*;

@Entity
@Table(name = "pessoas")
@Data

public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column (nullable = false)
    private String nome;

    @Column (nullable = false )
    private String email;

    @Column (nullable = false)
    private String senha;

    @Column (nullable = false)
    private boolean perfil;

    private  boolean ativo = true;

    public String getNome(){
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public boolean isPerfil (){
        return perfil;
    }

    public void setPerfil(Boolean perfil){
        this.perfil= perfil;
    }

    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}

