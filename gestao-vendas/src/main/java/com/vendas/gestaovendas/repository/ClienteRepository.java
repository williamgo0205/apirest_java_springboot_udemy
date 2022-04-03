package com.vendas.gestaovendas.repository;

import com.vendas.gestaovendas.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository  extends JpaRepository<Cliente, Long> {
    // Busca cliente pelo nome
    Cliente findByNome(String nome);
}
