package com.github.marcuslucas.livraria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.marcuslucas.livraria.dto.LivroCreateRequest;
import com.github.marcuslucas.livraria.dto.LivroMapper;
import com.github.marcuslucas.livraria.dto.LivroResponse;
import com.github.marcuslucas.livraria.dto.LivroUpdateRequest;
import com.github.marcuslucas.livraria.model.Livro;
import com.github.marcuslucas.livraria.service.LivroService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Livros")
@RestController
@RequestMapping("/api/v1/livros")
public class LivroController {

    @Autowired
    private LivroService service;

    @Autowired
    private LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<LivroResponse> create(@Valid @RequestBody LivroCreateRequest dtoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroMapper.toDto(
                service.createOrUpdate(
                        livroMapper.toModel(dtoRequest)
                )
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> findById(@PathVariable Long id) {
        return service
                .findById(id)
                .map(livro -> livroMapper.toDto(livro))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LivroResponse>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(livro -> livroMapper.toDto(livro))
                        .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody LivroUpdateRequest dtoRequest) {

        if (service.findById(id).isPresent()) {
            Livro livro = livroMapper.toModel(id, dtoRequest);
            return ResponseEntity.ok(livroMapper.toDto(
                    service.createOrUpdate(livro)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
