package com.catalogoLojaProdutos.service;

import com.catalogoLojaProdutos.Dto.RedefinirSenhaDTO;
import com.catalogoLojaProdutos.model.Pessoa;
import com.catalogoLojaProdutos.model.TokenRedefinicao;
import com.catalogoLojaProdutos.repository.PessoaRepository;
import com.catalogoLojaProdutos.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class usuarioService {

    @Autowired private PessoaRepository pessoaRepository;
    @Autowired private TokenRepository tokenRepository;
    @Autowired private JavaMailSender mailSender;
    @Autowired private PasswordEncoder passwordEncoder; // BCrypt (ver item 5.1)

    /**
     * Gera token, salva no banco e envia e-mail com link para redefinição.
     */
    public void enviarTokenRedefinicao(String email) {
        Pessoa usuario = pessoaRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = UUID.randomUUID().toString();

        TokenRedefinicao tokenEntity = new TokenRedefinicao();
        tokenEntity.setToken(token);
        tokenEntity.setUsuario(usuario);
        tokenEntity.setExpiracao(LocalDateTime.now().plusMinutes(15)); // token expira em 15 min
        tokenRepository.save(tokenEntity);

        // Link que será aberto pelo usuário (Angular irá ler o token da querystring)
        String link = "http://localhost:4200/redefinir-senha?token=" + token;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(usuario.getEmail());
        mail.setSubject("Redefinição de senha");
        mail.setText("Olá, " + usuario.getNome()
                + "\n\nRecebemos uma solicitação de redefinição de senha."
                + "\nClique no link abaixo (válido por 15 minutos):"
                + "\n" + link
                + "\n\nSe você não fez essa solicitação, ignore este e-mail.");

        mailSender.send(mail);
    }

    /**
     * Valida o token e redefine a senha, apagando o token em seguida.
     */
    public void redefinirSenha(RedefinirSenhaDTO dto) {
        TokenRedefinicao token = tokenRepository.findByToken(dto.getToken())
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (token.getExpiracao().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado. Solicite novamente.");
        }

        Pessoa usuario = token.getUsuario();
        usuario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        pessoaRepository.save(usuario);

        tokenRepository.delete(token); // invalida o token usado
    }
}
