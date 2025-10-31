package com.catalogoLojaProdutos.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

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

