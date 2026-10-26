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

import com.github.marcuslucas.livraria.dto.LivrariaCreateRequest;
import com.github.marcuslucas.livraria.dto.LivrariaMapper;
import com.github.marcuslucas.livraria.dto.LivrariaResponse;
import com.github.marcuslucas.livraria.dto.LivrariaUpdateRequest;
import com.github.marcuslucas.livraria.model.Livraria;
import com.github.marcuslucas.livraria.service.LivrariaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Livrarias")
@RestController
@RequestMapping("/api/v1/livrarias")
public class LivrariaController {

    @Autowired
    private LivrariaService service;

    @Autowired
    private LivrariaMapper livrariaMapper;

    @PostMapping
    public ResponseEntity<LivrariaResponse> create(@Valid @RequestBody LivrariaCreateRequest dtoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livrariaMapper.toDto(
                service.createOrUpdate(
                        livrariaMapper.toModel(dtoRequest)
                )
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivrariaResponse> findById(@PathVariable Long id) {
        return service
                .findById(id)
                .map(livraria -> livrariaMapper.toDto(livraria))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LivrariaResponse>> findAll() {
        return ResponseEntity.ok(
                service.findAll().stream()
                        .map(livraria -> livrariaMapper.toDto(livraria))
                        .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivrariaResponse> update(@PathVariable Long id,
                                                      @Valid @RequestBody LivrariaUpdateRequest dtoRequest) {

        if (service.findById(id).isPresent()) {
            Livraria livraria = livrariaMapper.toModel(id, dtoRequest);
            return ResponseEntity.ok(livrariaMapper.toDto(
                    service.createOrUpdate(livraria)));
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
