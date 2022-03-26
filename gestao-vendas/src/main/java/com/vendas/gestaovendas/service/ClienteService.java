package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Busca todos os Clientes
    public List<Cliente> listarTodos(){
        return clienteRepository.findAll();
    }

    // Busca apenas o cliente repassado por codigo
    public Optional<Cliente> buscarPorCodigo(Long codigo){
        return clienteRepository.findById(codigo);
    }
}
