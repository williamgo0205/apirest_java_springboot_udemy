package com.vendas.gestaovendas.repository;

import com.vendas.gestaovendas.entity.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    // Busca os objetos e retorna os dados através do NEW
    @Query("select new com.vendas.gestaovendas.entity.ItemVenda("
         + "       iv.codigo, "
         + "       iv.quantidade, "
         + "       iv.precoVendido, "
         + "       iv.produto, "
         + "       iv.venda)"
         + "  from ItemVenda iv"
         + " where iv.venda.codigo = :codigoVenda")
    List<ItemVenda> buscarPorCodigo(Long codigoVenda);
}
