package com.vendas.gestaovendas.factory;

import com.vendas.gestaovendas.dto.cliente.model.ClienteRequestDTO;
import com.vendas.gestaovendas.dto.cliente.model.ClienteResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;

public abstract class ClienteMockFactory {

    public static Cliente createCliente(Long codigo, String nome, String telefone, Boolean ativo, String logradouro,
                                  Integer numero, String complemento, String bairro, String cep, String cidade,
                                  String estado) {
        Cliente clienteCriado = new Cliente();
        clienteCriado.setCodigo(codigo);
        clienteCriado.setNome(nome);
        clienteCriado.setTelefone(telefone);
        clienteCriado.setAtivo(ativo);
        clienteCriado.setEndereco(
                EnderecoMockFactory.createEndereco(logradouro, numero, complemento, bairro, cep, cidade, estado));
        return clienteCriado;
    }

    public static ClienteResponseDTO createClienteResponseDTO(Long codigo, String nome, String telefone, Boolean ativo,
                                                              String logradouro, Integer numero, String complemento,
                                                              String bairro, String cep, String cidade, String estado) {
        return new ClienteResponseDTO(codigo, nome, telefone, ativo,
                EnderecoMockFactory.createEnderecoResponseDTO(logradouro, numero, complemento, bairro,
                        cep, cidade, estado));
    }

    public static ClienteRequestDTO createClienteRequestDTO(String nome, String telefone, Boolean ativo,
                                                            String logradouro, Integer numero, String complemento,
                                                            String bairro, String cep, String cidade, String estado) {
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setNome(nome);
        clienteRequestDTO.setTelefone(telefone);
        clienteRequestDTO.setAtivo(ativo);
        clienteRequestDTO.setEnderecoRequestDTO(EnderecoMockFactory.createEnderecoRequestDTO(logradouro, numero,
                complemento, bairro, cep, cidade, estado));

        return clienteRequestDTO;
    }
}
