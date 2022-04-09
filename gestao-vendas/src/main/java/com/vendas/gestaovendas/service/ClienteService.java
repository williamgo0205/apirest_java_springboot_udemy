package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.exception.RegraNegocioException;
import com.vendas.gestaovendas.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    //Metodo para atualizar de Cliente  no banco de dados
    public Cliente atualizar(Long codigo, Cliente cliente) {
        Cliente clienteAtualizar = validaClienteExiste(codigo);
        validarClienteDuplicado(cliente);
        /* BeanUtils substitui a entidade recebida via parametro no banco de dados
          > SOURCE = entidade a ser salva (recebida por parametro)
          > TARGET = entidade do banco de dados
          > Terceiro parâmetro = campo que não deve ser modificado nessa acao*/
        BeanUtils.copyProperties(cliente, clienteAtualizar, "codigo");

        // Persiste a entidade no banco de dados
        return clienteRepository.save(clienteAtualizar);
    }

    private Cliente validaClienteExiste(Long codigo) {
        Optional<Cliente> cliente = buscarPorCodigo(codigo);
        // Caso o cliente nao exista lanca uma exception
        if (cliente.isEmpty()){
            throw new EmptyResultDataAccessException(1);
        }
        return cliente.get();
    }

    private void validarClienteDuplicado(Cliente cliente) {
        Cliente clientePorNome = clienteRepository.findByNome(cliente.getNome());
        if (clientePorNome != null && clientePorNome.getCodigo() != cliente.getCodigo()) {
            throw new RegraNegocioException(String.format("O Cliente %s já está cadastrada.",
                    cliente.getNome().toUpperCase()));
        }
    }
}
