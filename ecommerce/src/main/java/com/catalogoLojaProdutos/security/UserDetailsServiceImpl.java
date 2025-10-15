package com.catalogoLojaProdutos.security;

import com.catalogoLojaProdutos.model.Pessoa;
import com.catalogoLojaProdutos.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Usuário fixo de exemplo (pode vir do banco depois)
        if ("joao".equals(username)) {
            return new User("joao", "", Collections.emptyList());
        }
        throw new UsernameNotFoundException("Usuário não encontrado");
    }
}

