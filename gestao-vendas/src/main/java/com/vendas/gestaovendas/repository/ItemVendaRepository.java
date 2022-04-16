package com.vendas.gestaovendas.repository;

import com.vendas.gestaovendas.entity.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    List<ItemVenda> findByVendaCodigo(Long codigoVenda);
}
