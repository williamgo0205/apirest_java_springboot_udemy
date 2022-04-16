package com.vendas.gestaovendas.dto.venda.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("Cliente da Venda Retorno DTO")
public class ClienteVendaResponseDTO {

    @ApiModelProperty(value = "Nome Cliente")
    private String nome;

    @ApiModelProperty(value = "Venda")
    private List<VendaResponseDTO> vendaResponseDTO;

    public ClienteVendaResponseDTO(String nome, List<VendaResponseDTO> vendaResponseDTOS) {
        this.nome = nome;
        this.vendaResponseDTO = vendaResponseDTOS;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<VendaResponseDTO> getVendaResponseDTO() {
        return vendaResponseDTO;
    }

    public void setVendaResponseDTO(List<VendaResponseDTO> vendaResponseDTO) {
        this.vendaResponseDTO = vendaResponseDTO;
    }
}
