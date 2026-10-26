package com.github.marcuslucas.livraria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.marcuslucas.livraria.model.Livro;
import com.github.marcuslucas.livraria.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    public Livro createOrUpdate(Livro livro) {
        return repository.save(livro);
    }

    public Optional<Livro> findById(Long id) {
        return repository.findById(id);
    }

    public List<Livro> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
