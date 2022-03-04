package com.vendas.gestaovendas.repository;

import com.vendas.gestaovendas.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Busca uma categoria pelo Nome Informado
    Categoria findByNome(String nome);
}
