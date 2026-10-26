package com.github.marcuslucas.livraria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.marcuslucas.livraria.model.Livraria;
import com.github.marcuslucas.livraria.repository.LivrariaRepository;

@Service
public class LivrariaService {

    @Autowired
    private LivrariaRepository repository;

    public Livraria createOrUpdate(Livraria livraria) {
        return repository.save(livraria);
    }

    public Optional<Livraria> findById(Long id) {
        return repository.findById(id);
    }

    public List<Livraria> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
