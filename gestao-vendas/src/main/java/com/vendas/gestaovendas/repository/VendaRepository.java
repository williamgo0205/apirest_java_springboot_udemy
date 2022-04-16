package com.vendas.gestaovendas.repository;

import com.vendas.gestaovendas.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByClienteCodigo(Long codigoCliente);
}
