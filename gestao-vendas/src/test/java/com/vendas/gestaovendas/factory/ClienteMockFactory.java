package com.vendas.gestaovendas.factory;

import com.vendas.gestaovendas.dto.cliente.model.ClienteResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;

public class ClienteMockFactory {

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
}
