package com.vendas.gestaovendas.factory;

import com.vendas.gestaovendas.dto.endereco.model.EnderecoResponseDTO;
import com.vendas.gestaovendas.entity.Endereco;

public class EnderecoMockFactory {

    public static Endereco createEndereco(String logradouro, Integer numero, String complemento, String bairro,
                                          String cep, String cidade, String estado) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);
        endereco.setBairro(bairro);
        endereco.setCep(cep);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        return endereco;
    }

    public static EnderecoResponseDTO createEnderecoResponseDTO(String logradouro, Integer numero, String complemento,
                                                                String bairro, String cep, String cidade,
                                                                String estado) {
        return new EnderecoResponseDTO(logradouro, numero, complemento, bairro, cep, cidade, estado);
    }
}
