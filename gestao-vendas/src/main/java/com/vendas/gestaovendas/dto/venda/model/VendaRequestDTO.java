package com.vendas.gestaovendas.dto.venda.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.List;

@ApiModel("Venda Requisição DTO")
public class VendaRequestDTO {

    @ApiModelProperty(value = "Data")
    private LocalDate data;

    @ApiModelProperty(value = "Itens da Venda")
    private List<ItemVendaRequestDTO> itensVendaRequestDTO;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<ItemVendaRequestDTO> getItensVendaRequestDTO() {
        return itensVendaRequestDTO;
    }

    public void setItensVendaRequestDTO(List<ItemVendaRequestDTO> itensVendaRequestDTO) {
        this.itensVendaRequestDTO = itensVendaRequestDTO;
    }
}
