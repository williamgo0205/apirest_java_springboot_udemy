package com.vendas.gestaovendas.repository;

import com.vendas.gestaovendas.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Busca um produto pela Descricao Informado
    Produto findByDescricao(String descricao);
}
