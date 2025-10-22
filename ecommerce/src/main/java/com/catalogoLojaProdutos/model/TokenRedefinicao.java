package com.catalogoLojaProdutos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens_redefinicao")
public class TokenRedefinicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 200)
    private String token;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pessoa_id")
    private Pessoa usuario; // Pessoa é seu usuário

    @Column(nullable = false)
    private LocalDateTime expiracao;

    public Long getId() {
        return id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Pessoa getUsuario() {
        return usuario;
    }
    public void setUsuario(Pessoa usuario) {
        this.usuario = usuario;
    }
    public LocalDateTime getExpiracao() {
        return expiracao;
    }
    public void setExpiracao(LocalDateTime expiracao) {
        this.expiracao = expiracao;
    }
}
