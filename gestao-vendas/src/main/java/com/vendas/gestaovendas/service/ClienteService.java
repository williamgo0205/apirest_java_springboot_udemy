package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.exception.RegraNegocioException;
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

    //Metodo Salvar Cliente
    public Cliente salvar(Cliente cliente) {
        validarClienteDuplicado(cliente);
        return clienteRepository.save(cliente);
    }

    private void validarClienteDuplicado(Cliente cliente) {
        Cliente clientePorNome = clienteRepository.findByNome(cliente.getNome());
        if (clientePorNome != null && clientePorNome.getCodigo() != cliente.getCodigo()) {
            throw new RegraNegocioException(String.format("O Cliente %s já está cadastrada.",
                    cliente.getNome().toUpperCase()));
        }
    }
}
