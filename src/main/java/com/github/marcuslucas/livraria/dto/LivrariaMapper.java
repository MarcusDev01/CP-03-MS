package com.github.marcuslucas.livraria.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.github.marcuslucas.livraria.model.Livraria;

@Component
public class LivrariaMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public Livraria toModel(LivrariaCreateRequest dto) {
        return modelMapper.map(dto, Livraria.class);
    }

    public LivrariaResponse toDto(Livraria entity) {
        return modelMapper.map(entity, LivrariaResponse.class);
    }

    public Livraria toModel(Long id, LivrariaUpdateRequest dto) {
        Livraria livraria = modelMapper.map(dto, Livraria.class);
        livraria.setId(id);
        return livraria;
    }
}
