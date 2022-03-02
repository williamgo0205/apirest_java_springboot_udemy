package com.vendas.gestaovendas.repository;

import com.vendas.gestaovendas.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Busca os produtos pela categoria Informado
    List<Produto> findByCategoriaCodigo(Long codigoCategoria);

    // Busca o produto pelo codigo e pela categoria Informado
    // Optional<Produto> findByCodigoAndCategoriaCodigo(Long codigo, Long codigoCategoria)
    @Query("Select prod"
         + "  from Produto prod"
         + " where prod.codigo = :codigo"
         + "   and prod.categoria.codigo = :codigoCategoria")
    Optional<Produto> buscaPorCodigoProduto(Long codigo, Long codigoCategoria);
}
