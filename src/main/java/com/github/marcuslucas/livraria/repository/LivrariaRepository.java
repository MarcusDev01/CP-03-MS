package com.github.marcuslucas.livraria.repository;

import com.github.marcuslucas.livraria.model.Livraria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivrariaRepository extends JpaRepository<Livraria, Long> {
}
