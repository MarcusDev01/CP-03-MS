package com.github.marcuslucas.livraria.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.github.marcuslucas.livraria.model.Livro;

@Component
public class LivroMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public Livro toModel(LivroCreateRequest dto) {
        return modelMapper.map(dto, Livro.class);
    }

    public LivroResponse toDto(Livro entity) {
        return modelMapper.map(entity, LivroResponse.class);
    }

    public Livro toModel(Long id, LivroUpdateRequest dto) {
        Livro livro = modelMapper.map(dto, Livro.class);
        livro.setId(id);
        return livro;
    }
}
