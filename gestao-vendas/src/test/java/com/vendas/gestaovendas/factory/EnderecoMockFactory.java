package com.vendas.gestaovendas.factory;

import com.vendas.gestaovendas.dto.endereco.model.EnderecoResponseDTO;
import com.vendas.gestaovendas.entity.Endereco;

public abstract class EnderecoMockFactory {

    public static Endereco createEndereco(String logradouro, Integer numero, String complemento, String bairro,
                                          String cep, String cidade, String estado) {
        return new Endereco(logradouro, numero, complemento, bairro, cep, cidade, estado);
    }

    public static EnderecoResponseDTO createEnderecoResponseDTO(String logradouro, Integer numero, String complemento,
                                                                String bairro, String cep, String cidade,
                                                                String estado) {
        return new EnderecoResponseDTO(logradouro, numero, complemento, bairro, cep, cidade, estado);
    }
}
