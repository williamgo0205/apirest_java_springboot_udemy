package com.vendas.gestaovendas.dto.cliente.model;

import com.vendas.gestaovendas.dto.endereco.model.EnderecoResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Cliente Retorno DTO")
public class ClienteResponseDTO {

    @ApiModelProperty(value = "Código")
    private Long codigo;

    @ApiModelProperty(value = "Nome")
    private String nome;

    @ApiModelProperty(value = "Telefone")
    private String telefone;

    @ApiModelProperty(value = "Ativo")
    private Boolean ativo;

    @ApiModelProperty(value = "Endereço DTO")
    private EnderecoResponseDTO enderecoResponseDTO;

    public ClienteResponseDTO(Long codigo, String nome, String telefone, Boolean ativo,
                              EnderecoResponseDTO enderecoResponseDTO) {
        this.codigo = codigo;
        this.nome = nome;
        this.telefone = telefone;
        this.ativo = ativo;
        this.enderecoResponseDTO = enderecoResponseDTO;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public EnderecoResponseDTO getEnderecoResponseDTO() {
        return enderecoResponseDTO;
    }

    public void setEnderecoResponseDTO(EnderecoResponseDTO enderecoResponseDTO) {
        this.enderecoResponseDTO = enderecoResponseDTO;
    }
}
