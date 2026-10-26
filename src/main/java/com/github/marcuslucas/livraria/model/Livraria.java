package com.github.marcuslucas.livraria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "livrarias")
public class Livraria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "especialidade", nullable = false)
    private String especialidade;

    @Column(name = "endereco", nullable = false)
    private String endereco;
}
