package com.github.marcuslucas.livraria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LivroCreateRequest {

    @NotBlank
    @Size(min = 2, message = "Título do livro deve ter no mínimo 2 caracteres")
    private String titulo;

    @NotNull(message = "Ano de publicação é obrigatório")
    private Integer anoPublicacao;

    @NotBlank(message = "Gênero é obrigatório")
    private String genero;

    @NotBlank(message = "Autor é obrigatório")
    private String autor;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

}
