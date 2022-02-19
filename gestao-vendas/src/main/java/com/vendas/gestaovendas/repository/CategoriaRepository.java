package com.vendas.gestaovendas.repository;

import com.vendas.gestaovendas.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
