package com.vendas.gestaovendas.dto.cliente.mapper;

import com.vendas.gestaovendas.dto.categoria.mapper.CategoriaMapper;
import com.vendas.gestaovendas.dto.cliente.model.ClienteResponseDTO;
import com.vendas.gestaovendas.dto.endereco.model.EnderecoResponseDTO;
import com.vendas.gestaovendas.dto.produto.model.ProdutoResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.entity.Produto;

public class ClienteMapper {

    // Conversor responsavel pela conversao dos dados de Cliente para a ClienteResponseDTO
    // Mapper propriamente dito
    public static ClienteResponseDTO converterParaClienteDTO(Cliente cliente) {
        EnderecoResponseDTO enderecoResponseDTO =
                new EnderecoResponseDTO(cliente.getEndereco().getLogradouro(),
                                        cliente.getEndereco().getNumero(),
                                        cliente.getEndereco().getComplemento(),
                                        cliente.getEndereco().getBairro(),
                                        cliente.getEndereco().getCep(),
                                        cliente.getEndereco().getCidade(),
                                        cliente.getEndereco().getEstado());

        return new ClienteResponseDTO(cliente.getCodigo(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getAtivo(),
                enderecoResponseDTO);
    }
}
