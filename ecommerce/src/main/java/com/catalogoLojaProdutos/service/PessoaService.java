package com.catalogoLojaProdutos.service;

import com.catalogoLojaProdutos.model.Pessoa;
import com.catalogoLojaProdutos.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa atualizar(Long id, Pessoa pessoaAtualizada) {
        return pessoaRepository.findById(id).map(pessoa -> {
            pessoa.setNome(pessoaAtualizada.getNome());
            pessoa.setEmail(pessoaAtualizada.getEmail());
            pessoa.setPerfil(pessoaAtualizada.isPerfil());
            pessoa.setSenha(pessoaAtualizada.getSenha());
            return pessoaRepository.save(pessoa);
        }).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    public void deletar(Long id) {
        pessoaRepository.deleteById(id);
    }
}
