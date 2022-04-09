package com.vendas.gestaovendas.dto.cliente.mapper;

import com.vendas.gestaovendas.dto.cliente.model.ClienteRequestDTO;
import com.vendas.gestaovendas.dto.cliente.model.ClienteResponseDTO;
import com.vendas.gestaovendas.dto.endereco.model.EnderecoResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.entity.Endereco;

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

    // Conversor responsavel pela conversao dos dados de ClienteRequestDTO para a Cliente
    public static Cliente converterParaEntidade(ClienteRequestDTO clienteRequestDTO) {
        Endereco endereco =
                new Endereco(clienteRequestDTO.getEnderecoRequestDTO().getLogradouro(),
                        clienteRequestDTO.getEnderecoRequestDTO().getNumero(),
                        clienteRequestDTO.getEnderecoRequestDTO().getComplemento(),
                        clienteRequestDTO.getEnderecoRequestDTO().getBairro(),
                        clienteRequestDTO.getEnderecoRequestDTO().getCep(),
                        clienteRequestDTO.getEnderecoRequestDTO().getCidade(),
                        clienteRequestDTO.getEnderecoRequestDTO().getEstado());

        return new Cliente(clienteRequestDTO.getNome(),
                clienteRequestDTO.getTelefone(),
                clienteRequestDTO.getAtivo(),
                endereco);
    }

    // Conversor responsavel pela conversao dos dados de ClienteRequestDTO para a Cliente
    public static Cliente converterParaEntidade(Long codigo, ClienteRequestDTO clienteRequestDTO) {
        Endereco endereco =
                new Endereco(clienteRequestDTO.getEnderecoRequestDTO().getLogradouro(),
                        clienteRequestDTO.getEnderecoRequestDTO().getNumero(),
                        clienteRequestDTO.getEnderecoRequestDTO().getComplemento(),
                        clienteRequestDTO.getEnderecoRequestDTO().getBairro(),
                        clienteRequestDTO.getEnderecoRequestDTO().getCep(),
                        clienteRequestDTO.getEnderecoRequestDTO().getCidade(),
                        clienteRequestDTO.getEnderecoRequestDTO().getEstado());

        return new Cliente(codigo,
                clienteRequestDTO.getNome(),
                clienteRequestDTO.getTelefone(),
                clienteRequestDTO.getAtivo(),
                endereco);
    }
}
